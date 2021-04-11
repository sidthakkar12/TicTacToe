/*

  Tic Tac Toe Backend App
  Owned by: Ryu Technologies
  Written by: Wyatt Mufson
  Started: March 2021

*/

const express = require('express');
const http = require('http');
const { networkInterfaces } = require('os');

const nets = networkInterfaces();
const app = express();

const server = http.createServer(app);
const io = require('socket.io')(server);
const routing = require('./routing.js');
const sockets = require('./sockets.js');

sockets(io);
routing(app, io); // Set API endpoints

function getLocalIP() {
  const names = Object.keys(nets);
  const namesCount = names.length;

  for (let i = 0; i < namesCount; i += 1) {
    const name = names[i];
    const netNames = nets[name];
    const netNamesCount = netNames.length;
    for (let j = 0; j < netNamesCount; j += 1) {
      const net = netNames[j];
      // Skip over non-IPv4 and internal (i.e. 127.0.0.1) addresses
      if (net.family === 'IPv4' && !net.internal) {
        if (name === 'en0') {
          return net.address;
        }
      }
    }
  }
  return '127.0.0.1';
}

// Start the server
const port = 3000;
const localIP = getLocalIP();
server.listen(port, () => {
  console.log(`Starting server available on:\nhttp://${localIP}:${port}\nhttp://localhost:${port}`);
});

const sigs = ['SIGINT', 'SIGTERM', 'SIGQUIT'];
sigs.forEach((sig) => {
  process.on(sig, () => {
    // Stops the server from accepting new connections and finishes existing connections.
    console.log('Closing server...');

    server.close((err) => {
      if (err) {
        console.log(`Error when closing: ${err.message}`);
        process.exit(1);
      }

      console.log('Server closed');
      process.exit(0);
    });
  });
});

module.exports = { app, server, port };
