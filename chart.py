import pandas as pd
import numpy as np
from matplotlib import pyplot as plt

df = pd.read_csv('output.csv')
df.plot.line(x='index', figsize=(18,10))
plt.savefig('fi_chart.png')

