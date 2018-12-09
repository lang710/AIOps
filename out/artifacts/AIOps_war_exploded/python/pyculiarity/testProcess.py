
import sys
import os
curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)

from pyculiarity import detect_ts
import pandas as pd
twitter_example_data = pd.read_csv('/Users/mac/IdeaProjects/AIOps/src/python/pyculiarity/raw_data.csv',usecols=['timestamp','count'])
results=detect_ts(twitter_example_data,max_anoms=0.02,direction='both',only_last='day')
print(str(results['anoms']['anoms']))
resultfile=open("/Users/mac/IdeaProjects/AIOps/src/python/pyculiarity/result.csv",'a')
resultfile.write(str(results['anoms']['anoms']))
