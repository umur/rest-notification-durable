const PORTS = [8081, 8082, 8083, 8084];
const DELAYED_PORT = 8085;
const http = require('http');

PORTS.forEach(PORT => {
    http.createServer((req, res) => {
        res.end(JSON.stringify({ received: true }))
    }).listen(PORT, _ => console.log(`listening on ${PORT}`))
})

setTimeout(() => {
    http.createServer((req, res) => {
        res.end(JSON.stringify({ received: true }))
    }).listen(DELAYED_PORT, _ => console.log(`listening on ${DELAYED_PORT}`))
}, 5000)