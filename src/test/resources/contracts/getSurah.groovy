description("Get Surah API")

request {
    method 'GET'
    url '/v1/surah/114/en.asad'
}

response {
    status 200
    body("""
        {
          "code": 200,
          "status": "OK",
          "data": {
            "number": 114,
            "name": "\\u0633\\u064f\\u0648\\u0631\\u064e\\u0629\\u064f \\u0627\\u0644\\u0646\\u0651\\u064e\\u0627\\u0633\\u0650",
            "englishName": "An-Naas",
            "englishNameTranslation": "Mankind",
            "revelationType": "Meccan",
            "numberOfAyahs": 6,
            "ayahs": [
              {
                "number": 6231,
                "text": "SAY: \\\"I seek refuge with the Sustainer of men,",
                "numberInSurah": 1,
                "juz": 30,
                "manzil": 7,
                "page": 604,
                "ruku": 556,
                "hizbQuarter": 240,
                "sajda": false
              },
              {
                "number": 6232,
                "text": "\\\"the Sovereign of men,",
                "numberInSurah": 2,
                "juz": 30,
                "manzil": 7,
                "page": 604,
                "ruku": 556,
                "hizbQuarter": 240,
                "sajda": false
              },
              {
                "number": 6233,
                "text": "\\\"the God of men,",
                "numberInSurah": 3,
                "juz": 30,
                "manzil": 7,
                "page": 604,
                "ruku": 556,
                "hizbQuarter": 240,
                "sajda": false
              },
              {
                "number": 6234,
                "text": "\\\"from the evil of the whispering, elusive tempter",
                "numberInSurah": 4,
                "juz": 30,
                "manzil": 7,
                "page": 604,
                "ruku": 556,
                "hizbQuarter": 240,
                "sajda": false
              },
              {
                "number": 6235,
                "text": "\\\"who whispers in the hearts of men",
                "numberInSurah": 5,
                "juz": 30,
                "manzil": 7,
                "page": 604,
                "ruku": 556,
                "hizbQuarter": 240,
                "sajda": false
              },
              {
                "number": 6236,
                "text": "\\\"from all [temptation to evil by] invisible forces as well as men,\\\"",
                "numberInSurah": 6,
                "juz": 30,
                "manzil": 7,
                "page": 604,
                "ruku": 556,
                "hizbQuarter": 240,
                "sajda": false
              }
            ],
            "edition": {
              "identifier": "en.asad",
              "language": "en",
              "name": "Asad",
              "englishName": "Muhammad Asad",
              "format": "text",
              "type": "translation",
              "direction": "ltr"
            }
          }
        }
    """)
    headers {
        contentType(applicationJson())
    }
}
