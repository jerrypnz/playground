#encoding=UTF-8
import base64
import re
import httplib, urllib
from xml.dom import minidom
from datetime import datetime

_dateReg = re.compile(r"\+\d{4}\s")
_defaultTimeFormat = "%Y-%m-%d %H:%M:%S"
_FANFOU_API_ADDRESS="api.fanfou.com"
_SEND_MSG="/statuses/update.xml"
_MY_MSG="/statuses/user_timeline.xml?id=%s&count=%d"
_FRIENDS_MSG="/statuses/friends_timeline.xml?id=%s&count=%d"
_PUBLIC_MSG="/statuses/public_timeline.xml?count=%d"

##################################General purpose functions###################################
def __fanfouGet__(url):
	"""Use HTTP get method to retrieve data from an url. Returns an instanse of HTTPResponse."""
	conn = httplib.HTTPConnection(_FANFOU_API_ADDRESS)
	conn.request("GET",url)
	response = conn.getresponse()
	if response.status != 200:
		raise HttpError(response.status, response.reason)
	else:
		return response

def __fanfouPost__(url, headers, params):
	"""Use HTTP post method to send some data to the fanfou. Return an instance of HTTPResponse."""
	conn = httplib.HTTPConnection(_FANFOU_API_ADDRESS)
	conn.request("POST",_SEND_MSG,params,headers)
	response = conn.getresponse()
	if response.status != 200:
		raise HttpError(response.status, response.reason)
	else:
		return response



def __parseTextNode__(nodeList):
	"""Parse an XML node, and extract the texts."""
	result = ""
	for node in nodeList:
		if node.nodeType == node.TEXT_NODE:
			result = result + node.data
	return result

def __parseStatus__(statusNode):
	"""Pase an <status> node of Fanfou XML data. Returns an instanse of Status"""
	createdTime = __parseTextNode__(statusNode.getElementsByTagName("created_at")[0].childNodes);
	realTime = datetime.strptime(_dateReg.sub("",createdTime),"%a %b %d %H:%M:%S %Y")
	text = __parseTextNode__(statusNode.getElementsByTagName("text")[0].childNodes);
	return Status(text,realTime)

class Error(Exception):
	"""Base Error class of this pyfanfou module"""
	pass

class HttpError(Error):
	"""HttpError represents an Http error with an error code(e.g. 404) and a reason"""
	def __init__(self,code,reason):
		self.errorCode = code
		self.errorReason = reason

	def __str__(self):
		return "Http Communication Error(status:%d, reason:%s)" % (self.errorCode, self.errorReason)



class Status:
	"""Status represents an Status object of Fanfou. Refer to http://help.fanfou.com/api.html"""
	text = ""
	createdTime = ""

	def __init__(self,text,createdTime):
		self.text = text
		self.createdTime = createdTime

	def printMe(self):
		print "-"*20
		print "Text: %s" % (self.text)
		print "Created at: %s" % (datetime.strftime(self.createdTime,_defaultTimeFormat))



class FanfouClient:
	username = ""
	password = ""
	_authStr = ""

	def __init__(self,username,password):
		self.username = username
		self.password = password
		self._authStr = base64.b64encode(username + ":" + password);
		#饭否API使用HTTP Basic认证，用户名和密码用base64编码
		print "[Authentication][Encoded] ", self._authStr

	def __parseFanfouXML__(self,reader):
		xmldoc = minidom.parse(reader)
		print xmldoc
		nodes = xmldoc.getElementsByTagName("status")
		list = [__parseStatus__(node) for node in nodes]
		return list


	def sendMessage(self,message):
		headers = {"Content-type": "application/x-www-form-urlencoded",
				"Accept": "text/xml",
				"Authorization": "Basic " + self._authStr}
		params = urllib.urlencode({"status": message})
		__fanfouPost__(_SEND_MSG, headers, params)

	def getMyMessages(self,count=10):
		response = __fanfouGet__(_MY_MSG %( self.username,count))
		return self.__parseFanfouXML__(response)

	def getFriendsMessages(self, count=10):
		response = __fanfouGet__(_FRIENDS_MSG %(self.username, count))
		return self.__parseFanfouXML__(response)

	def getPublicMessages(self, count=10):
		response = __fanfouGet__(_PUBLIC_MSG % (count))
		return self.__parseFanfouXML__(response)

def testSendMessage(client):
	msg = ""
	while msg == "":
		msg = raw_input("Input some message:")
	client.sendMessage(msg)

def testMyMessage(client):
	msgs = client.getMyMessages()
	for a in msgs:
		a.printMe()

def testFriendsMessage(client):
	msgs = client.getFriendsMessages()
	for a in msgs:
		a.printMe()

def testPublicMessage(client):
	msgs = client.getPublicMessages()
	for a in msgs:
		a.printMe()

if __name__ == "__main__":
	username = 'xxxxxxx'
	password = 'xxxxxxx'
	client = FanfouClient(username,password)
	print "My messages","="*20
	testMyMessage(client)
	print "Friends' and my messages", "="*20
	testFriendsMessage(client)
	print "Public messages", "="*20
	testPublicMessage(client)
