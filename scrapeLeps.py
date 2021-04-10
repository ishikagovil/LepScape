# run using python3
# install requests, bs4 using pip3
# current issues: not writing anything to .csv

import requests
from bs4 import BeautifulSoup

# open the file; set to be able to write
filename = 'leps.csv'
f = open(filename, 'w')

#change ending number for pagination /2 /3,etc
for i in range(97):
    # first page; initialization
    url = "https://www.nwf.org/NativePlantFinder/Wildlife/Butterflies-and-Moths"

    if i != 1:
        url = url + "/" + i

    page_html = requests.get(url)

    html_soup = BeautifulSoup(page_html.content, "html.parser")

    lep_items = html_soup.find_all('div', class_="tileContainer")

    headers = "Common Name, Genera Name, Family Name \n"

    f.write(headers)

    for lep in lep_items:
        commonName = lep.find('span', class_="commonName").text
        generaName = lep.find('span', class_="generaName").text
        familyName = lep.find('span', class_="familyName").text

        print(commonName + " " + generaName + " " + familyName)

        #f.write(commonName + ", " + generaName + ", " +  familyName)
    


f.close()
