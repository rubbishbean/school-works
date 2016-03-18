#cs455 PA1 server Part1
#by Jiaxing Yan
#This is the server for PA1 part1. It has a port number and return the message
#catched to client

import socket

serverHost = ''  #localhost
serverPort = input('port:')

s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind((serverHost,serverPort))
s.listen(1)
print('Server started')
connect,addr = s.accept()
print('Request from',addr)


while 1:
    data = connect.recv(1024)
    if not data:  #check if data valid
        break
    print('Data Received: ' ,data)
    connect.send(data)
connect.close()
    
