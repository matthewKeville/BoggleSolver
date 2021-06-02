import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec
import csv
import statistics as stat


def boardAnalysis(size,pos):
    fileString="data"+str(size)+".csv"
    dens = []
    time = []
    with open(fileString, newline='') as csvfile:
        reader = csv.reader(csvfile,delimiter=',')
        for row in reader:
                dens.append(int(row[0]))
                time.append(int(row[1]))
    #convert nano time to seconds
    time = [t/1000000000 for t in time]
    ax = plt.subplot(gs[pos,0])
    ax.set_title('Board Density '+str(size)+"x"+str(size))
    dens_mean = stat.mean(dens)
    dens_std = stat.stdev(dens)
    dens_max = max(dens)
    dens_min = min(dens)
    plt.hist(dens,bins=60)
    time_mean = stat.mean(time)
    time_std = stat.stdev(time)
    time_max = max(time)
    time_min = min(time)
    ax = plt.subplot(gs[pos,1])
    ax.set_title('Solve Time '+str(size)+"x"+str(size))
    plt.hist(time,bins=60)

    print(""+str(size)+"x"+str(size))
    print("Mean : " + str(dens_mean) + "Standard Deviation : " + str(dens_std) +
        "Max : " + str(dens_max) + "Min : " + str(dens_min))
    print("Mean : " + str(time_mean) + "Standard Deviation : " + str(time_std) +
        "Max : " + str(time_max) + "Min : " + str(time_min))
    return


if __name__ == "__main__":

    fig = plt.figure(figsize=(12,12))
    fig.suptitle("Boggle Analysis")
    gs = gridspec.GridSpec(4,2,hspace=1/2)

    tests = [4,10,100,1000]
    for t in tests:
        boardAnalysis(t,tests.index(t))

    plt.show()


