#!/usr/bin/env groovy

// =============================================================================
// git repozitory : https://github.com/amkarnaf/am-je-file-stud.git
// file name      : jefile.groovy
// =============================================================================

/* *****************************************************************************
 * (AlexM) simple job
***************************************************************************** */


/* *****************************************************************************
 * function    : do_writeCsv
 * description : write object as CSV file
 * see here    : https://www.jenkins.io/doc/pipeline/steps/pipeline-utility-steps/
***************************************************************************** */
def do_writeCsv(wd, fnx, xdata)
{
  dir(wd)
  {
    writeCSV file: fnx, records: xdata, format: CSVFormat.EXCEL
  }
  return;
}


/* *****************************************************************************
 * function    : do_main_part
 * description : call write csv file function
***************************************************************************** */
def do_main_part(wd, sfnm_csv, sdir_csv)
{
  def valPASS = "PASS";
  def valFAIL = "FAIL";

  def breA = false; // predefined value as 'ERROR' state
  def breF = false;

  def liREC = [];
  def liCSV = [];

  dir(wd)
  {
    // make table header as record
    liREC[0] = "Test name";
    liREC[1] = "Test result";
    liCSV.add(liREC);

    // =============================================================================
    // CSV file: create PASS record
    // =============================================================================
    liCSV.add(["Pupsik", valFAIL]);
    liCSV.add(["Mopsik", valPASS]);
    liCSV.add(["Yupsik", valFAIL]);
    liCSV.add(["}I{onna", valPASS]);
  }

  liCSV_sz = liCSV.size();
  if(liCSV_sz > 0)
  {
    def fnx = sfnm_csv;
    println("==== Write CSV file ${fnx}, size : ${liCSV_sz} ... ====");
    println("${liCSV}");
    do_writeCsv(sdir_csv, sfnm_csv, liCSV);
  }
}


println("==== (AM) Start script... ====");

/* *****************************************************************************
 * function    : pipeline
 * description : declarative style
***************************************************************************** */
pipeline
{
  //agent { label "virt-Ubu18" }
  agent any

  parameters
  {
    string(name: 'ATIME', defaultValue: '3', description: 'Timeout in seconds');
    string(name: 'SLEEP_TIME', defaultValue: '10', description: 'Sleep time in seconds');
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
          sh "echo '=1a=' && whoami && pwd && echo 'Go!' ";
          sh """
          if [ ! -d /home/jenkins/oudir/ ]; then
            mkdir /home/jenkins/oudir/
            echo "==== (AM) The output dir created... ===="
          else
            echo "==== (AM) The output dir already exists... ===="
          fi
          """
          sh """
            echo "#### Create text file B ####"
            echo "echo ==== This is file 'helo.txt' ====" > /home/jenkins/oudir/helo.txt
            echo "Good day, dear AlexM! How are You?" >> /home/jenkins/oudir/helo.txt
            echo "#### Create text file E ####"
          """
          sh "echo '=1b='"
        }
        script
        {
          println("==== Script to create CSV file on the agent machine ====");
          def sfnm_csv = "csvJe.csv";
          def sdir_csv = "/home/jenkins/oudir";
          def wdir = "/home/jenkins/oudir"
          do_main_part(wdir, sfnm_csv, sdir_csv);
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
            //sleep 10;
            sleep params.SLEEP_TIME;
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
