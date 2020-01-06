It shows all the available information about the desired build. Note that the
build needs to be available in Jenkins to be displayed locally. If the build has
been pruned remotely, no cached version is kept locally, so the build is lost
forever.

## Usage
Example:
```
jkk status
```

This will return something like that:
```
Latest build on job Test
Build id: 1
Build url: http://jenkinsurl.example.com/url/to/build
Status: completed
Duration: 35

Title: #1
Description:

```

Note that currently, the build status can only be `building` or `completed`. In
future versions, this behavior will be improved to return the exact build status
(contemplating failure, success, etc).

## FAQ

> I want to see previous builds, how can I do?

Please check out the `show` or `log` command, that gives the possibility
respectively to show a build details (given its build id) or print a build list
for that job.
