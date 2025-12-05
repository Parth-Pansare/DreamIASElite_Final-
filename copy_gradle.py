import shutil, os 
src=r'C:\Users\Parth\.gradle\wrapper\dists\gradle-8.13-bin\5xuhj0ry160q40clulazy9h7d\gradle-8.13' 
dst=r'.gradle-local\wrapper\dists\gradle-8.13-bin\5xuhj0ry160q40clulazy9h7d\gradle-8.13' 
print('copying', src) 
print('exists', __import__('os').path.exists(src)) 
__import__('shutil').copytree(src, dst, dirs_exist_ok=True) 
print('done')
