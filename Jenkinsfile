node() {
stage ('Checkout'){
  checkout scm
}

stage ('Build')
{
 try {
    sh 'chmod a+x ./gradlew'
    sh './gradlew clean assembleRelease'
    currentBuild.result='SUCCESS'
    echo "RESULT: ${currentBuild.result}"
    echo "RESULT: ${currentBuild.previousBuild.result}"
    if(currentBuild.previousBuild.result!=null && currentBuild.previousBuild.result.toString().equals('SUCCESS'))
    {
     sendEmails('''Hi,
                      build succeded,after failure.''')
                      }
    }
   catch (Exception e) {
   currentBuild.result='FAILURE'
      sendEmails('''Hi,
                  build failed, please see logs...''' +e.getMessage())
      }
}
stage ('Report'){
        sh './gradlew lint'
        androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/lint-results*.xml', unHealthy: ''
}

}
def sendEmails(msg) {
  emailext attachLog: true,body: msg, subject: '$PROJECT_NAME - Build # $BUILD_NUMBER -'+currentBuild.result, to: 'amar.tyagi@kelltontech.com'
}