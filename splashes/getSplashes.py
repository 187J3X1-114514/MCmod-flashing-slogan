import requests, time, tqdm, os, re, sys
from bs4 import BeautifulSoup
import signal

isExit = False
os.chdir("splashes")


def getSp(htmlt):
    soup = BeautifulSoup(
        re.compile('<div class="shadow">.+</div>').search(htmlt).group(0), "html.parser"
    )
    return soup.get_text().replace("\n", "")


def getMCmodIndex():
    r = requests.get("https://www.mcmod.cn")
    if r.status_code != 200:
        sys.exit(114514)
    return r.text


def Merge(list1, list2):
    out = list(set(list1))
    list2 = list(set(list2))
    return out + [item for item in list2 if item not in list1]


def getSplashes(count, oldList, SleepTime=0.5):
    oldListCount = len(oldList)
    oldList1 = []
    for i in oldList:
        oldList1.append(i.replace("\n", ""))
    oldList = oldList1
    oldList = Merge(oldList, oldList)
    out = oldList
    tq = tqdm.tqdm(range(0, count), leave=False)
    for cou in range(0, count):
        if not isExit:
            time.sleep(SleepTime)
            Start = time.time()
            HtmlText = getMCmodIndex()
            Splash = getSp(HtmlText)
            Done = time.time()
            if Splash in out:
                tq.desc = f"'{Splash}' 用时{round(Done-Start,3)}秒 (已存在)"
                tq.update(1)
                continue
            else:
                tq.desc = f"'{Splash}' 用时{round(Done-Start,3)}秒 (新)"
                tq.update(1)
                out.append(Splash)
        else:
            tq.desc = f"准备退出..."
            newListCount = len(out)
            return (out, newListCount - oldListCount, newListCount)
    newListCount = len(out)
    return (out, newListCount - oldListCount, newListCount)


def Main(count, old, new=None, SleepTime=0.5):
    if new == None:
        new = old
    old = open(old, "r", encoding="utf8")
    Splashes = getSplashes(count, old.readlines(), SleepTime)
    old.close()
    new = open(new, "w", encoding="utf8")
    OutList = []
    for i in Splashes[0]:
        OutList.append(str(i).replace("\n", "") + "\n")
    new.writelines(OutList)
    new.close()
    print(
        f"完成,总共 {Splashes[2]} 条,新增 {Splashes[1]} 条,新标语概率 {round(Splashes[1]/count,5)*100}%"
    )


def handler(signal, frame):
    global isExit
    isExit = True


signal.signal(signal.SIGINT, handler)

Main(20000, "../src/main/resources/assets/minecraft/texts/splashes.txt")
