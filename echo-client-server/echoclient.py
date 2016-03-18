#cs455 PA1 client Part2
#by Jiaxing Yan
#This is the extended client for part2
#Change SDelay for delay test
#Change MSize to test message with different sizes


import socket
import time
import math

host = input('hostname:')
port = input('port:')

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((host,port))
#initialize 
PPhase = 's' #PROTOCOL PHASE 
WS = ' '
MType = 'rtt'#MEASUREMENT TYPE
NOP = '10'   #NUMBER OF PROBES
MSize = '1'#MESSAGE SIZE
SDelay = '10'#SERVER DELAY
RTT=[]
TPUT=[]
#send in form <PROTOCOL PHASE><WS><MEASUREMENT TYPE><WS><NUMBER OF
#PROBES><WS><MESSAGE SIZE><WS><SERVER DELAY>\n
s.send(PPhase+WS+MType+WS+NOP+WS+MSize+WS+SDelay+'\n')
response = s.recv(1024)
#analyze the response
while response[-1] != '\n': #check if the response is complete
    response += s.recv(1024)
if response == '200 OK: Ready\n':
    print('Response from server'+ response)
    PPhase = 'm'  #set phase to m and do the next protocol
    Pay = 'pay*'+MSize #for checking whether 
    for i in range(1,int(NOP)+1):
        #<PROTOCOL PHASE><WS><PROBE SEQUENCE NUMBER><WS><PAYLOAD>\n
        s.send(PPhase+WS+str(i)+WS+Pay+'\n')
        tStart = time.time()
        response2 = s.recv(64000)
        while response2[-1] != '\n':
            response2 += s.recv(64000)
        tEnd = time.time()
        t = tEnd - tStart
        RTT +=[t*1000]
        TPUT +=[(int(MSize)*8)/(t*1000)]
        print('Response from server:' + response2)
    #check for CTP protocol in form <PROTOCOL PHASE>\n
    PPhase = 't'
    s.send(PPhase + '\n')

    response3 = s.recv(1024)
    while response3[-1] != '\n':
        response3 += s.recv(1024)
    if response3 == '200 OK: Closing Connection\n':
        s.close()
    print('Response from server:' + response3)
else:
    s.close()
s.close()

#calculate the average and standard deviation
finRTT = 0
finTPUT = 0
for i in range(len(RTT)):
    finRTT += RTT[i]
    finTPUT += TPUT[i]
meanRTT = finRTT/len(RTT)
meanTPUT = finTPUT/len(RTT)
sdRTT = 0
sdTPUT = 0
for j in RTT:
    sdRTT = sdRTT+(j-meanRTT)*(j+meanRTT)
sdRTT = math.sqrt(1.0*sdRTT/len(RTT))
for k in TPUT:
    sdTPUT = sdTPUT+(k-meanTPUT)*(k+meanTPUT)
sdTPUT = math.sqrt(1.0*sdTPUT/len(RTT))


print 'The mean of RTT is: ',meanRTT,'ms  The standard deviation of RTT is: ',sdRTT
print 'The mean of Throughput is: ',meanTPUT,'kbps   The standard deviation of RTT is: ',sdTPUT
        
    

