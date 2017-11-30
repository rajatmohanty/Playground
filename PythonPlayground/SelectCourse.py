# coding=utf-8
import urllib
import urllib2
import cookielib
import re
import string

#  保存并读取 cookie
filename = 'cookie.txt'
# 声明一个MozillaCookieJar对象实例来保存cookie，之后写入文件
cookie = cookielib.MozillaCookieJar(filename)
opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cookie))

postdata = urllib.urlencode({
    'userName': '141210026',
    'password': '**'
    })
# 登录教务系统的URL
loginUrl = 'http://jw.nju.edu.cn:8080/jiaowu/login.do'
# 模拟登录，并把cookie保存到变量
result = opener.open(loginUrl, postdata)

# 保存cookie到cookie.txt中
cookie.save(ignore_discard=True, ignore_expires=True)

commonUrl = 'http://jw.nju.edu.cn:8080/jiaowu/student/elective/courseList.do'

campus = urllib.urlencode({
	'method': 'discussRenewCourseList',
	'campus': '仙林校区'
})

campusResult = opener.open(commonUrl, campus)

print campusResult

# commonPage = opener.open(commonUrl)
# courseItems = re.findall(r'<tr class="TABLE_TR_.*?">'
# 			r'<td valign="middle"><a href="javascript:showCourseDetailInfo.*?"><u>.*?</u></a></td>'
# 			r'<td valign="middle">.*?</td>'
# 			r'<td valign="middle">(.*?)</td>'
# 			r'<td align="center" valign="middle">(.*?)</td>'
# 			r'<td valign="middle">(.*?)</td>'
# 			r'<td valign="middle">(.*?)</td>'
# 			r'<td align="center" valign="middle">(.*?)</td>'
# 			r'<td align="center" valign="middle">(.*?)</td>'
# 			r'<td valign="middle"></td>'
# 			r'<td align="center" valign="middle"><input type="radio" name="selectedRadio" value=".*?"></td></tr>',commonPage.read())
# print courseItems[0]
# print courseItems[1]
# print courseItems[2]
# print courseItems[3]
# print courseItems[4]
