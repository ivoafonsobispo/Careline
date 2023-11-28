//
//  Network.swift
//  CarelineSwifty
//
//  Created by formando on 28/11/2023.
//

import SwiftUI

class TemperatureAPI {
    
    func getTemperature(completion: @escaping (Temperature) -> ()) {
        guard let url = URL(string: "http://10.20.229.121/temperature") else {fatalError("Incorrect URL")}
        
        let urlRequest = URLRequest(url: url)
        
        let dataTask = URLSession.shared.dataTask(with: urlRequest) { (data, response, error) in
            if let error = error {
                print("Request error.", error)
                return
            }
            
            guard let response = response as? HTTPURLResponse else {return}
            
            if response.statusCode == 200 {
                guard let data = data else {return }
                
                DispatchQueue.main.async {
                    do {
                        let decodedTemperature = try JSONDecoder().decode(Temperature.self, from: data)
                        print("Temperatureeeeeeeeeeeeee \(decodedTemperature)")
                        completion(decodedTemperature)
                    } catch {
                        print("Error WOWOWOWOWOWOWWOW. \(error)")
                    }
                }
            }
        }
        
        dataTask.resume()
    }
}


