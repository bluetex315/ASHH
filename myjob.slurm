#!/bin/bash 
#SBATCH -J myjob
#SBATCH -p kshdnormal
#SBATCH -n 32
#SBATCH -N 2
#SBATCH --ntasks-per-node=16
#SBATCH --gres=dcu:0
#SBATCH --time 12:00:00 
#SBATCH --comment=BASE 
###SBATCH -o std.out.%j
###SBATCH -e std.err.%j
#SBATCH -o /public/home/dku_mmustafa/std_out/std.out.%j
#SBATCH -e /public/home/dku_mmustafa/std_err/std.err.%j
###SBATCH -a 0-1   ### MARK_MULTI_SUB

### Get parameters from GUI
MIDFILE_DIR=/public/home/dku_mmustafa
source $MIDFILE_DIR/job_portal.var
source $MIDFILE_DIR/job_interface.var

### Set basic var   ### MARK_slurm2pbs

JOBID=$SLURM_JOB_ID                                  ### slurm2pbs
NP=$SLURM_NPROCS                                     ### slurm2pbs
NNODE=`srun hostname | sort | uniq | wc -l`          ### slurm2pbs

LOG_FILE=$WORK_DIR/log_file/job_${JOB_NAME}_${JOBID}.log
HOST_FILE=$WORK_DIR/host_file/job_${JOB_NAME}_${JOBID}_${NP}c_${NNODE}n.ma 

srun hostname | sort | uniq -c |awk '{print $2":"$1}' > $HOST_FILE  ### slurm2pbs

### Write basic job infomations

echo -e "The start time is: `date +"%Y-%m-%d %H:%M:%S"` \n" | tee -a $LOG_FILE 
echo -e "My job ID is: $JOBID \n" | tee -a $LOG_FILE  
echo -e "The total cores is: $NP \n" | tee -a $LOG_FILE 
echo -e "The hosts is: \n" | tee -a $LOG_FILE
cat $HOST_FILE | tee -a $LOG_FILE
echo -e "\n"  | tee -a $LOG_FILE 

### Run APP

  # MARK_CMD  #Don't delete this line!!!
#!/bin/bash

HHARRAY=("AcceptAllHH" "FairShareILS" "GIHH" "ACO" "Ant-Q" "avegnep" "bader")
###HHARRAY=("AcceptAllHH" "FairShareILS" "LeanGIHH" "GIHH" "ACO" "Ant-Q" "avegnep" "bader" "DynILS" "GenHive" "GISS" "HAEA" "HAHA" "ISEA" "KSATS-HH" "MCHH-S" "ML" "NAHH" "PHUNTER" "SA-ILS" "SELF-SEARCH" "VNS-TW" "XCJ")
PROBARRAY=("SAT" "Binpacking" "FlowShop" "TSP" "VRP")
###INSARRAY=(50)
TIMEARRAY=(600 6000)

### i1 is the element of the array found with ARRAY_ID mod INS_ARRAY_LENGTH

HH=${HHARRAY[`expr $SLURM_ARRAY_TASK_ID / 10 % ${#HHARRAY[@]}`]}
prob=${PROBARRAY[`expr $SLURM_ARRAY_TASK_ID / 2 % ${#PROBARRAY[@]}`]}
time=${TIMEARRAY[`expr $SLURM_ARRAY_TASK_ID % ${#TIMEARRAY[@]}`]}

echo "printed info------"
echo $SLURM_ARRAY_TASK_ID
echo $HH
echo $prob
echo $time

###java -jar 10_17.jar $HH $prob 50 1 600
java -jar 10_17.jar AcceptAllHH SAT 50 1 600
echo "finished"

  # MARK_BASH #Don't delete this line!!!


echo The end time is: `date +"%Y-%m-%d %H:%M:%S"` | tee -a $LOG_FILE   :wq