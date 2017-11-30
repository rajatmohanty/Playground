# coding=utf-8
import urllib
import urllib2
import cookielib

# 需要定义一个字典，名字为values，参数我设置了userName和password
values = {"userName": "141210026", "password": "**"}
# 定义 header
user_agent = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:50.0) Gecko/20100101 Firefox/50.0'
headers = {'User-Agent': user_agent}
# 利用urllib的urlencode方法将字典编码，命名为data
data = urllib.urlencode(values)
url = "http://jw.nju.edu.cn:8080/jiaowu/login.do"
# 构建request时传入两个参数，url和data
request = urllib2.Request(url, data, headers)
# 异常处理
try:
    urllib2.urlopen(request)
except urllib2.URLError, e:
    print e.code
    print e.reason
response = urllib2.urlopen(request)
print response.read()

# 声明一个CookieJar对象实例来保存cookie
cookie = cookielib.CookieJar()
# 利用urllib2库的HTTPCookieProcessor对象来创建cookie处理器
handler = urllib2.HTTPCookieProcessor(cookie)
# 通过handler来构建opener
opener = urllib2.build_opener(handler)
# 此处的open方法同urllib2的urlopen方法，也可以传入request
response = opener.open(request)
for item in cookie:
    print 'Name  = ' + item.name
    print 'Value = ' + item.value
