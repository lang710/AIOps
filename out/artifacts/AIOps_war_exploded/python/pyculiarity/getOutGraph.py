import sys
import os
curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)

from pyculiarity import detect_ts
import pandas as pd
import matplotlib.pyplot as plt

twitter_example_data = pd.read_csv('/Users/mac/IdeaProjects/AIOps/out/artifacts/AIOps_war_exploded/python/pyculiarity/raw_data.csv',usecols=['timestamp','count'])
results=detect_ts(twitter_example_data,max_anoms=0.02,direction='both',only_last='day')

results['anoms'].to_csv('results_dataframe.csv')

#result1:
#print(results['anoms']['anoms'])

rawtimeX=[i for i in twitter_example_data['timestamp']]
rawvalueY=[j for j in twitter_example_data['value']]
timeX=[i for i in results['anoms']['timestamp']]
anomsY=[j for j in results['anoms']['anoms']]

xmin ,xmax = min(rawtimeX), max(rawtimeX)
ymin, ymax = min(rawvalueY), max(rawvalueY)

fig=plt.figure()
ax=fig.add_subplot(111)

dx = (xmax - xmin) * 0.1
dy = (ymax - ymin) * 0.1

plt.xlim(xmin - dx, xmax + dx)
plt.ylim(ymin - dy, ymax + dy)

ax.scatter(timeX,anomsY,color='red')
ax.plot(rawtimeX,rawvalueY)

plt.xlabel('Timestamp')
plt.ylabel('value')

plt.savefig("/Users/mac/IdeaProjects/AIOps/out/artifacts/AIOps_war_exploded/python/pyculiarity/results.png",dpi=72)
#plt.show()

print("results.png")


