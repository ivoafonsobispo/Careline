//
//  Network.swift
//  CarelineSwifty
//
//  Created by formando on 28/11/2023.
//

import SwiftUI

class TemperatureAPI {
    
    func getTemperature(completion: @escaping (Double) -> ()) {
        guard let url = URL(string: "http://10.20.229.61/temperature") else {fatalError("Incorrect URL")}
        
        let urlRequest = URLRequest(url: url)
        
        let dataTask = URLSession.shared.dataTask(with: urlRequest) { (data, response, error) in
            if let error = error {
                print("Request error.", error)
                return
            }
            
            guard let response = response as? HTTPURLResponse else {return}
            
            if response.statusCode == 200 {
                guard let value = data else {return }
                
                DispatchQueue.main.async {
                    do {
                        let json = try JSONSerialization.jsonObject(with: value, options: []) as? [String: Any]
                        
                        if let temperatureValue = json?["value"] as? Double {
                            print("Temperatureeeeeeeeeeeeee \(temperatureValue)")
                            completion(temperatureValue)
                        } else {
                            print("Error: Unable to extract temperature value from the JSON.")
                        }
                    } catch {
                        print("Error WOWOWOWOWOWOWWOW. \(error)")
                    }
                }
            }
        }
        
        dataTask.resume()
    }
}

