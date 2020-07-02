import os, re
from shutil import copyfile


path = './output-folder/'
new_path = './workspace/converted/'
last_path = './workspace/final/'
entries = os.listdir(new_path)

def copy_file():
    for f in os.listdir(path):
        if re.match('xss', f):
            copyfile(path + os.sep  +f, new_path+f)

def convert_file():
    for i in entries:
        pre = os.path.splitext(i)[0]
        command = ('dot -Tjpg '+new_path+i+ ' > '+last_path+ pre + '.jpg')
        os.system(command)

#copy_file()
convert_file()
