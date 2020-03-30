package org.rmc33.lernajenkins

class Utilities {

   static def findChangedPackages(steps, targetBranch) {
        def changedPackages = new HashSet<String>();
        steps.checkout scm
        String diffFilesList = steps.sh(script: "git diff remotes/origin/${targetBranch} --name-only", returnStdout: true)
        steps.echo "change branch ${env.CHANGE_BRANCH} diff : ${diffFilesList}-"
        List<String> files = Arrays.asList(diffFilesList.split("\\r?\\n"))
        files.each { file ->
            def matcher = file =~ /packages\/(.*?)\//
            if (matcher) {
                changedPackages.add(matcher[0][1])
            }
        }
        changedPackages
    }

}