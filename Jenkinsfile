node() {
stage ('Checkout'){
  checkout scm
}

stage ('Build'){
 try {
    sh 'chmod a+x ./gradlew'
    sh './gradlew clean assembleRelease'
    if(build.previousBuild.result.toString().equals('FAILURE'))
    {
     sendEmails('''Hi,
                      Build succeded after failure.''')
    }
    }
   catch (Exception e) {
   sendEmails('''Hi,
                  build failed, please see logs...''')
      }
}
stage ('Report'){
        sh './gradlew lint'
        androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/lint-results*.xml', unHealthy: ''
}

}
def sendEmails(msg) {
  emailext body: msg, subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!', to: 'amar.tyagi@kelltontech.com'
}