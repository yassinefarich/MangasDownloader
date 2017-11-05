import os,glob
import os, zipfile

def main():
   for directory in os.listdir('.'):
         if not os.path.isfile(directory):
            os.rename(directory,directory.zfill(3))

if __name__ == '__main__':
    main()
