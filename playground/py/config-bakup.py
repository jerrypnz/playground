import zipfile
import time
import os.path

# Consider getting this via API so that it is platform independent
TMP_STORE_DIR='/tmp/config_file_bakup'
IGNORE_FILES=['CVS','.svn']


class ConfigFileBackupTask(object):
    """Config file backup task"""
    def __init__(self, pkg_name, file_set, dir=""):
        self.pkg_name = pkg_name
        self.file_set = file_set
        self.store_dir = dir

    def _make_pkg(self):
        """Make the zip package and store them in the /tmp directory"""
        if not os.path.exists(TMP_STORE_DIR):
            os.mkdir(TMP_STORE_DIR)

        zip_file_name = "%s/%s_%s.zip" % (TMP_STORE_DIR,
                self.pkg_name,
                time.strftime('%Y%m%d_%H%M', time.localtime()))

        tmp_pkg = zipfile.ZipFile(zip_file_name, 'w', zipfile.ZIP_DEFLATED)

        # Add the files to the zip package
        for entry_name in self.file_set:
            # expand the ~ in the paths
            entry_name = os.path.expanduser(entry_name)

            if os.path.isfile(entry_name):
                file_name = os.path.basename(entry_name)
                tmp_pkg.write(entry_name, file_name)
            elif os.path.isdir(entry_name):
                parent_dir_name = os.path.dirname(entry_name)
                os.path.walk(entry_name, walk_callback, (tmp_pkg, parent_dir_name))

        return zip_file_name

def walk_callback(arg, dirname, fnames):
    """Callback function used when adding the files of a folder to a zip file"""
    zip_file, parent_dir = arg
    fnames[:] = [ f for f in fnames if not f in IGNORE_FILES ]
    for file in fnames:
        abs_path = os.path.join(dirname, file)
        if os.path.isfile(abs_path):
            ac_name = "%s/%s" % ( os.path.relpath(dirname, parent_dir), file)
            zip_file.write(abs_path, ac_name)

def test_zip_file():
    """test zip file"""
    task = ConfigFileBackupTask('foo', ('~/.vimrc', '~/.vim') )
    task._make_pkg()

if __name__ == "__main__":
    #test_zip_file()
    print globals()
