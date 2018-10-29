import os
import urllib.request

def save_img(img_url,file_name,file_path='/Users/kusong/Desktop/RY'):
    #保存图片到磁盘文件夹 file_path中，默认为当前脚本运行目录下的 book\img文件夹
    try:
        print(img_url)
        if not os.path.exists(file_path):
            os.makedirs(file_path)
        file_suffix = os.path.splitext(img_url)[1]
        filename = '{}{}{}{}'.format(file_path,os.sep,file_name,file_suffix)
        urllib.request.urlretrieve(img_url,filename=filename)
    except IOError as e:
        print('文件操作失败')
    except Exception as e:
        print('错误', e)

if __name__ == '__main__':
    base_url = 'http://sokarisinteractive.com/prj/russel/images/1/'
    for x in range(1,100000):
        img_url = base_url + str(x) + ".jpg"
        save_img(img_url, 'ry_' + str(x))
