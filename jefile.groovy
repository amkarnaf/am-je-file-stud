#!/usr/bin/env groovy

// =============================================================================
// git repozitory : https://github.com/amkarnaf/am-je-file-stud.git
// file name      : jefile.groovy
// =============================================================================

/* *****************************************************************************
 * (AlexM) simple job
***************************************************************************** */

println("==== (AM) Start script... ====");

pipeline
{
  agent any

  parameters
  {
    string(name: 'ATIME', defaultValue: '3', description: 'Timeout in seconds');
    string(name: 'NAME', defaultValue: 'Dear AlexM', description: "Say helo to : ");
  }
  stages
  {
  stage("Helo-stage")
  {
    steps
    {
      echo "Hello, ${params.NAME}!";
    }
  }
    stage("3-times-echo-stage")
    {
      steps
      {
        script
        {
          3.times
          {
            sh "echo '... Helo from start! ...'";
          }
        }
      }
    }
    stage("Do some commands...")
    {
      steps
      {
        script
        {
          sh "echo 'One, two, three...'";
          sh "echo '=1=' && whoami && pwd";
        }
      }
    }
    stage ("Do time stamps too...")
    {
      steps
      {
        timestamps
        {
          script
          {
            try
            {
              sh "echo '==== 1 Linux OS ===='";
            }
            catch(Exception e)
            {
              print "(AM) This is exception..."
            }
          }
          script
          {
            sleep 60
            currentBuild.result = "SUCCESS";
            sh "echo '==== 2 Linux OS ===='";
          }
        }
      }
    }
    stage("Time out...")
    {
      steps
      {
        timeout(time: params.ATIME, unit: 'SECONDS')
        {
          print("Here You may add testing for machine state if need");
        }
        script
        {
          currentBuild.result = "SUCCESS";
          sh "echo 'Four, five, six...'";
          sh "echo '=2=' && whoami && pwd";
        }
      }
    }
    stage("Hello after other stages...")
    {
      steps
      {
        script
        {
          sh "echo 'Helo after other stages...'";
        }
      }
    }
  }
}

println("==== (AM) Stopp script... ====");


/* *****************************************************************************

println("==== (AM) Start script... ====");

pipeline
{
  agent none

  stages
  {
    stage("Build!")
    {
      steps
      {
        echo "Building..."
      }
    }
    stage("Test")
    {
      steps
      {
        echo "Testing..."
      }
    }
    stage("Deploy")
    {
      steps
      {
        echo "Deploying..."
      }
    }
  }
}

println("==== (AM) Stopp script... ====");


***************************************************************************** */

