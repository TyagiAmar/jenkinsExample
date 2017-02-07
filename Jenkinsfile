node() {
try {
def emailTo='amar.tyagi@kelltontech.com'
        stage ('Checkout'){
          checkout scm
        }

        stage ('Build')
        {
            sh 'chmod a+x ./gradlew'
            sh './gradlew clean assembleRelease'
            if(currentBuild.previousBuild.result!=null && !currentBuild.previousBuild.result.toString().equals('SUCCESS'))
            {
                 sendEmails('''Hi,build succeded,after failure.''',emailTo)
            }

        }

        stage ('Report'){
                sh './gradlew lint'
                androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/lint-results*.xml', unHealthy: ''
        }
        currentBuild.result='SUCCESS'
    }
    catch (Exception e) {
        currentBuild.result='FAILURE'
        sendEmails('''Hi,build failed, please see logs...''' +e.getMessage(),emailTo)
    }

}

  // stage ('upload')
  //      {
  //          try
  //          {
  //          androidApkUpload apkFilesPattern: '**/*.apk', googleCredentialsId: 'AmarExample', recentChangeList: [[language: 'es-US', text: 'New changes.']], trackName: 'alpha'
  //          }
  //          catch(Exception e)
  //          {
  //          sendEmails('''Hi,build not uploaded on play store , please see logs...''' +e.getMessage(),emailTo)
  //          }
  //      }*/

def sendEmails(msg,toSend) {

   emailext attachLog: true,body: msg, subject: '$PROJECT_NAME - Build # $BUILD_NUMBER -'+currentBuild.result, to:toSend
}