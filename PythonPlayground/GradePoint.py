# coding=utf-8
import urllib
import urllib2
import cookielib
import re
import string
import json

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
# 成绩页面
commonUrl = 'http://jw.nju.edu.cn:8080/jiaowu/student/studentinfo/achievementinfo.do?method=searchTermList'
commonPage = opener.open(commonUrl)
termItems = re.findall(r'<tr align="center" height="22">\s+<td><a  href="(.*?)".*?</a> </td>\s+</tr>',
                       commonPage.read())
# termItems.pop(0)
totalGradePointSum = 0.0
totalPointSum = 0.0
scoreItems = []
# 对每一个学期的成绩统计
for item in termItems:
    gradePointSum = 0.0
    pointSum = 0.0
    # 利用cookie请求访问另一个网址
    gradeUrl = 'http://jw.nju.edu.cn:8080/jiaowu/' + item
    # 请求访问该网址
    gradePage = opener.open(gradeUrl)
    # 正则表达式读取
    detailScoreItems = re.findall('<td valign="middle">(.*?)</td>'
                                  '<td valign="middle">(.*?)</td>'
                                  '<td align="center" valign="middle">\s+(.*?)\s+</td>.*?'
                                  '<td align="center" valign="middle">(.*?)</td>.*?'
                                  '<td align="center" valign="middle">\s+<ul\s+style="color:black"\s+>\s+(.*?)\s+</ul>\s+</td>',
                                  gradePage.read(), re.S)
    # 对每一项课程的成绩统计
    for item in detailScoreItems:
        print '====='
        print item[0]
        print item[1]
        print item[2]
        print item[3]
        print item[4]
        # if item[2] != '':
        #     gradePointSum += (string.atof(item[2]) * string.atof(item[3]))
        #     pointSum += string.atof(item[2])
    # scoreItems.append([gradePointSum / pointSum / 20, pointSum])
    # totalGradePointSum += gradePointSum
    # totalPointSum += pointSum
# 打印每一学期的GPA
# print '====='
# print 'Each Term Grade Point:'
# for item in scoreItems:
#     print item[0], item[1]
# 打印总GPA
# print '====='
# print 'Total Grade Point:'
# print (totalGradePointSum / totalPointSum) / 20, totalPointSum
