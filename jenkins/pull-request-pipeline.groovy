import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    return Utilities.listChangedPackagesGitDiff(steps, 'remotes/origin/develop')
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}

def runAfterPackagesPipeline(script) {

}

return this;