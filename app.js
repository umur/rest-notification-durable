
const fetch = require('node-fetch');
const { from, Observable } = require('rxjs');
const { tap, retryWhen, delay, take, flatMap } = require('rxjs/operators');


const servers = [
    'http://localhost:8081',
    'http://localhost:8082',
    'http://localhost:8083',
    'http://localhost:8084',
    'http://localhost:8085',
];

servers.forEach((SERVER, index) => {
    Observable.create(async observer => {
        let response;
        try {
            const start = Date.now();
            response = await fetch(SERVER)
            console.log(`Server ${index + 1}`, Date.now() - start, 'ms')
        } catch (error) {
            observer.error(error)
        }
        observer.next(response)
    }).pipe(
        retryWhen(errors =>
            errors.pipe(
                tap(val => console.log(`Retrying Server ${index + 1}`)),
                delay(2000), //restart in 2 seconds
                take(5)
            )
        ),
        flatMap(res => {
            if (res) return from(res.json())
            else return from([null])
        })
    )
        .subscribe(console.log)
})

