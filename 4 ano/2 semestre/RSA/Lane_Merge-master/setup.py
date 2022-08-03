from setuptools import setup

setup(
    name = 'laneMerge', 
    version = '1.0', 
    description = 'Scripts for the web app Lane Merge',
    author = 'Tiago Dias & Martim Neves',
    package_dir = { 'script': 'script',
                    'script.msg': 'script/msg',
                    'script.test': 'script/test'
                  },
    packages = ['script', 'script.msg', 'script.test']
)