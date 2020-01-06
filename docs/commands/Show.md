It shows the build details, giving the possibility to have a plain view of the
build just like from the Jenkins UI.

This command has not been implemented yet!

## Usage
Example:
```
jkk show
```

Where:
* `--show-log -l` shows all the log (note: retrieving the full build log can be 
time and resource consuming, client and server sides), adding at the end of the
output the following lines:
```
Log:
<the actual log>
```
* `--show-job -j` additionally prints the job name too, adding it after the 
first output line the following line:
```
Job name: Test
```

The output is something like this:
```
On build 1
Build title: #1
Description:

Started by: admin@example.com
Started on: 1970/01/01 01:11+01
Completed on: 1970/01/01 01:12+01
Duration: 1m
URL: http://jenkinsurl.example.com/url/to/build
```

Note: if not specified, the build shown is the last one.
