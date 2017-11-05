import os
import os, zipfile

def zip_folder(folder):
    print("processing: " + folder)
    # concatenate the folders for file name
    zipfilename = folder+".cbz"
    zfile = zipfile.ZipFile(zipfilename, 'w', zipfile.ZIP_DEFLATED)
    # rootlen => zipped files don't have a deep file tree
    rootlen = len(folder) + 1
    for base, dirs, files in os.walk(folder):
        for file in files:
            fn = os.path.join(base, file)
            zfile.write(fn, fn[rootlen:])
    zfile.close()


def main():
    for fn in os.listdir('.'):
         if not os.path.isfile(fn):
            zip_folder(fn)

if __name__ == '__main__':
    main()