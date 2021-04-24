
# This program uses the Trefle REST API to request plant data from a database and store it as a JSON Object.
# Resource: https://trefle.io/

# The json package should come by default.
# To install the requests package, type "pip install requests" into the terminal.

import requests
import json
import csv

def printAsFormattedJSON(jsonObject):
    """Prints JSON as a multi-line, indented string rather than a jumbled mess. 
       Truncates string that are more than 1,000 characters."""
    print(json.dumps(jsonObject, indent=2)[0:1000])

# You must create an account on Trefle IO to get an API Key.
# Go to https://trefle.io/users/sign_up and create an account.
# If you then go to your Account page, you will find your key.
# Copy the key into the variable below.
key = "PklZD7nSq3xQVGWKD7H6WQdyWdny23lU8sgjZAVuZ28"

f = open('finalPlantListWithInfo.csv', mode='w')
headers = "Genera Name, Species Name, Common Name, Spread, Leps, Woody or Herbaceous, Light, Soil, Moisture, ImageURL \n"
f.write(headers)


with open('finalPlantList.csv', newline='') as csv_file:
    data = list(csv.reader(csv_file, delimiter=','))

# print(data)

    # The following is an unexaustive demonstration of the data that you can get from the JSON object parsed above.
    # I have supplied you with most of the important data that you will need for your application; however,
    # I expect that you will need more data than I have provided. If you do need more data, the documentation 
    # https://docs.trefle.io/docs/advanced/plants-fields includes all of the information that comes with the
    # plant JSON Object. If you study the code written below, it should be relatively easy to figure out how
    # to access the other information indicated in the documentation.

    # All of the comments that I listed below came from the documentation. If you have any questions, the documentation
    # is probably a good start: https://docs.trefle.io/docs/advanced/plants-fields

for plantData in data:
    plantName = plantData[1] + "-" + plantData[2]
    r = requests.get("https://trefle.io/api/v1/species/" + plantName + "?token=" + key)

    plant = r.json()["data"]

    #print("Species Data")
    commonName = plant["common_name"]   # The usual common name, in english, of the species (if any).
    if commonName is None:
        commonName = "noCommonName"
    print("Common Name: ", commonName)
    scientificName = plant["scientific_name"]   # The scientific name follows the Binomial nomenclature, and represents its genus and its species within the genus.
    nameSplit = scientificName.split()
    genusName = ""
    genusName += nameSplit[0]
    speciesName = ""
    speciesName += nameSplit[1]
    print("Scientific Name: ", scientificName)
    #print("\n")
    #print("Image Data")
    #imageData = plant["images"]['']
    # printAsFormattedJSON(imageData)

    # There is more than just one image in the imageData, I just happened to only grab the first one.
    #image = imageData[0]
    #print(image)
    imageUrl = plant["image_url"]
    if imageUrl is None:
        imageUrl = "noImage"

    print("URL:", imageUrl)     # If you take this URL and put into a "new Image(imageURL)" statement in Java, it will create an image using that URL instead of a local one.
                                # It is not strictly necessary for you to save any of these images to your local computer; however, we would recommend that you do so to allow
                                # you to use those images without internet access.
    #print("\n")
    #print("Growth Data")
    growthData = plant["growth"]
    # printAsFormattedJSON(growthData)
    spread = growthData["spread"]["cm"] 
    if spread is None:
        spread = "0"
    #print("Spread: ", spread)   # The average spreading of the plant, in centimeters
    #phMinimum = growthData["ph_minimum"]    # The minimum acceptable soil pH (of the top 30 centimeters of soil) for the plant
    #print(phMinimum)
    #phMaximum = growthData["ph_maximum"]    # The maximum acceptable soil pH (of the top 30 centimeters of soil) for the plant
    #print(phMaximum)

    light = growthData["light"]     # Required amount of light, on a scale from 0 (no light, <= 10 lux) to 10 (very intensive insolation, >= 100 000 lux)
    if light is None:
        light = "-1"
    #print("Light: ", light)
    soil = growthData["soil_texture"]   # Required texture of the soil, on a scale from 0 (clay) to 10 (rock)
    if soil is None:
        soil = "-1"
    #print("Soil: ", soil)
    atmosphericHumidity = growthData["atmospheric_humidity"]    # Required relative humidity in the air, on a scale from 0 (<=10%) to 10 (>= 90%)
    if atmosphericHumidity is None:
        atmosphericHumidity = "-1"
    #print("Moisture: ", atmosphericHumidity)
    #str(scientificName)
    #str(commonName)
    spreadStr = ""
    spreadStr += str(spread)
    leps = ""
    leps += str(plantData[4])
    woodherb = ""
    woodherb += str(plantData[3])
    lightStr = ""
    lightStr += str(light)
    soilStr = ""
    soilStr += str(soil)
    atmoStr = ""
    atmoStr += str(atmosphericHumidity)

    #print(type(genusName), ",", type(speciesName), ",", type(commonName), ",", type(spread), ",", type(leps), ",", type(woodherb), ",", type(lightStr), ",", type(soilStr), ",", type(atmoStr))

    dataText = ""
    dataText += genusName + "," + speciesName + "," + commonName + "," + spreadStr + "," + leps + "," + woodherb + "," + lightStr + "," + soilStr + "," + atmoStr + "," + imageUrl + "\n"
    #print(dataText)
    f.write(dataText)

    print("\n----")
    #print("Specification Data")
    #specificationData = plant["specifications"]
    # printAsFormattedJSON(specificationData)

    #averageHeight = specificationData["average_height"]["cm"]
    #print("Average Height: ", averageHeight)
f.close()
