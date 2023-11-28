//
//  Measure.swift
//  CarelineSwifty
//
//  Created by formando on 16/11/2023.
//

import Foundation

struct Measure: Codable, Identifiable {
    var id = UUID()
    
    var symbol: String
    var name: String
    var data: String
    var metric: String
    var measured: Bool = false
    
    static var baseMeasures: [Measure] {
        return [
            Measure(symbol: "heart", name: "Heartbeat", data: "-1", metric: "BPM"),
            Measure(symbol: "thermometer", name: "Body Temperature", data: "-1", metric: "ºC"),
        ]
        
    }
    
}
