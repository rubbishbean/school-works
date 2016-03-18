#cs455 PA1 server Part2
#by Jiaxing Yan
#This is the extended server for part2
#It checks the protocol formats and return message to client
#A bug may occur when set delay to 0
import socket
import time

serverHost = ''
serverPort = input('port:')

s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind((serverHost,serverPort))
s.listen(1)
print('The server is ready')

connect,addr = s.accept()

data = connect.recv(1024)
while data[-1] != '\n': #check if the message catched is complete
    data += connect.recv(1024)
#split by ' '    
dataS = data.split(' ')
#check for the CSP protocol
if dataS[0] == 's' and (dataS[1] == 'rtt' or dataS[1] == 'tput'): 
    if dataS[2] != None and dataS[3] != None and dataS[4] != None: #check for format
        NOP = int(dataS[2])
        MSize = int(dataS[3])
        SDelay = int(dataS[4])
        connect.send('200 OK: Ready\n')
    else:
        connect.send('404 ERROR: Invalid Connection Setup Message\n')
        connect.close()
    

#count for PROBE SEQUENCE NUMBER
    for i in range(1,NOP+1):
        data2 = connect.recv(64000)
        while data2[-1] != '\n':
            data2 += connect.recv(64000)
        data2S = data2.split()
        getPSNum = int(data2S[1])
        if data2S[0] != 'm' or getPSNum != i:
            connect.send('404 ERROR: Invalid Connection Setup Message\n')
            connect.close()
        else:
            time.sleep(SDelay/1000.0) #server delay
            connect.send(data2)
    data3 = connect.recv(1024)

    #check format of CTP protocol
    if data3 == 't\n':
        connect.send('200 OK: Closing Connection\n')
    else:
        connect.send('404 ERROR: Invalid Connection Setup Message\n')
        
    connect.close()
else:
    connect.send('404 ERROR: Invalid Connection Setup Message\n')
    connect.close()
    
        
