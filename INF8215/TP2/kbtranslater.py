import sys
f = open(sys.argv[1])
data = f.read()
data = data.split()
for i in range(len(data)):
	data[i]= data[i].split(',')

for j in range(1,len(data[0])):
	print('')
	for i in range(1,len(data)):
		if(data[i][j] == 'y'):
			toprint = data[0][j] + '(yes,' + data[i][0]+').'
		elif(data[i][j] == 'n'):
			toprint = data[0][j] + '(non,' + data[i][0]+').'
		else:
			toprint = data[0][j] + '(yes,' + data[i][0]+').'
			toprint += '\n'+data[0][j] + '(non,' + data[i][0]+').'
		print(toprint)
