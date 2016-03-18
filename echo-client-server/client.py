#cs455 PA1 client Part1
#by Jiaxing Yan
# This is the client for PA1 part1 which simply ask you for a hostname and port
# and then ask you to send message if connect successfully.It then cathes the response
import socket

host = input('hostname:')  #enter host name
port = input('port:')      #enter port 

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((host,port))
message = raw_input('your message:') #enter message
s.send(message)
response = s.recv(1024)
print('Response from server: '+response) #response get
print('')
s.close()
