import plotly
from plotly.graph_objs import *
import mysql.connector
import pandas as pd
print (plotly.__version__)

myDB = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="123456"
)

c=myDB.cursor()
c.execute("USE cis")
# 1 major count
c.execute("SELECT mjr, count(mjr) from hw group by mjr")
myresult=c.fetchall()
df = pd.DataFrame([[ij for ij in i] for i in myresult])
df.rename(columns={0:'major', 1:'major_counts'}, inplace=True);
print(df)
trace1 = Bar(x=df['major'], y=df['major_counts'],
        marker=dict(
        color='rgb(244,66,80)',
        line=dict(
        color='rgb(244,66,80)',
        width=1.5),
        ))
# 2 home country count
c.execute("SELECT home, count(home) from hw group by home")
myresult2=c.fetchall()
df2 = pd.DataFrame([[ij for ij in i] for i in myresult2])
df2.rename(columns={0:'home', 1:'home_counts'}, inplace=True);
print(df2)
trace2 = Bar(x=df2['home'], y=df2['home_counts'],visible=False,
        marker=dict(
        color='rgb(15,202,225)',
        line=dict(
        color='rgb(15,202,225)',
        width=1.5,
        )))

# 3 Average GPA by majors
c.execute("select mjr, avg(gpa) as AVG_GPA from hw group by mjr")
myresult3=c.fetchall()
df3 = pd.DataFrame([[ij for ij in i] for i in myresult3])
df3.rename(columns={0:'major', 1:'gpa_avg'}, inplace=True);
print(df3)
trace3 = Bar(x=df3['major'], y=df3['gpa_avg'],visible=False,
        marker=dict(
        color='rgb(58,20,225)',
        line=dict(
        color='rgb(58,20,225)',
        width=1.5),
        ))

# 4 Attempted credits by years
c.execute("select yr, avg(atmpted) from hw group by yr")
myresult4=c.fetchall()
df4 = pd.DataFrame([[ij for ij in i] for i in myresult4])
df4.rename(columns={0:'year', 1:'credits_avg'}, inplace=True);
print(df4)
trace4 = Bar(x=df4['year'], y=df4['credits_avg'],visible=False,
        marker=dict(
        color='rgb(58,10,225)',
        line=dict(
        color='rgb(58,10,225)',
        width=1.5),
        ))

# 5 My own stuff
c.execute("select gender, avg(gpa) from hw group by gender")
myresult5=c.fetchall()
df5 = pd.DataFrame([[ij for ij in i] for i in myresult5])
df5.rename(columns={0:'home', 1:'gpa_avg'}, inplace=True);
print(df5)
trace5 = Bar(x=df5['home'], y=df5['gpa_avg'],visible=False,
        marker=dict(
        color='rgb(105,8,140)',
        line=dict(
        color='rgb(105,8,140)',
        width=1.5),
        ))

updatemenus = list([
            dict(
                buttons=list([
                    dict(
                        args=['type', 'bar'],
                        label='Bar chart',
                        method='restyle',
                        ),
                    dict(
                        args=['type', 'scatter'],
                        label='Line chart',
                        method='restyle'
                        ),
                    dict(
                        label='Student counts of majors',
                        method='update',
                        args=[{'visible':[True,False,False,False,False]},{
                        'title':'The number of students group by major'}]
                    ),
                    dict(
                        label='Students counts of country',
                        method='update',
                        args=[{'visible':[False,True,False,False,False]},{
                        'title':'The number of students group by country'}]
                    ),
                    dict(
                        label='Avg GPA for each major',
                        method='update',
                        args=[{'visible':[False,False,True,False,False]},{
                        'title':'The average GPA group by major'}]
                    ),
                    dict(
                        label='Avg atmpd crd per yr',
                        method='update',
                        args=[{'visible':[False,False,False,True,False]},{
                        'title':'The average credits attempted each year'}]
                    ),
                    dict(
                        label='Average GPA compared w gender',
                        method='update',
                        args=[{'visible':[False,False,False,False,True]},{
                        'title':'The average GPA compared with gender'}]
                    )
                    ]),
                    direction='left',
                    pad = {'r': 10, 't': 10},
                    showactive = True,
                    type = 'buttons',
                    x = 0,
                    xanchor = 'left',
                    y = 0,
                    yanchor = 'top'
                    ),
])

layout = dict(title='Number of students group by major\n',
                showlegend=False,
                updatemenus=updatemenus)

data = [trace1, trace2, trace3, trace4, trace5]


annotations = list([
    dict(text='',x=0, y=1.085, yref='paper', align='left', showarrow=False)
])
layout['updatemenus'] = updatemenus
layout['annotations'] = annotations


plotly.offline.plot({
    "data" : data,
    "layout" : layout}
)
