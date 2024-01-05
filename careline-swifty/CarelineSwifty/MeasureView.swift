//
//  MeasureView.swift
//  CarelineSwifty
//
//  Created by formando on 17/11/2023.
//

import SwiftUI

import HealthKit
import AVFoundation

struct MeasuringTextView: View {
    var body: some View {
        HStack(alignment: .center) {
            VStack(alignment: .leading) {
                Text ("WEDNESDAY, OCT 18")
                    .foregroundColor(.gray)
                    .font(.caption)
                Text("Measuring")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
            }
        }
        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 50, alignment: .leading)
    }
}


// MARK: Measure Value View
struct MeasureValueView: View {
    var measure: Measure
    
    func stringValue(from measure: Measure) -> String {
        if let _ = measure as? Temperature {
            var temperatureValue = "0.0"
            // TODO: API Temperature
            
            return temperatureValue
        } else if let heartbeat = measure as? Heartbeat {
            return "\(heartbeat.value)"
        }else {
            return "N/A"
        }
    }
    
    var body: some View {
        HStack{
            VStack(alignment: .leading){
                Text("Reading:")
                HStack{
                    Text("\(stringValue(from: measure))")
                        .font(.custom("", fixedSize: 50))
                        .fontWeight(.bold)
                    Text(measure.metric)
                }.padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 20))
                Text ("WEDNESDAY, OCT 18 AT 15:35")
                    .foregroundColor(.gray)
                    .font(.caption)
            }.frame(minWidth: 210, maxWidth: 210, alignment: .leading)
            Image(systemName: measure.symbol)
                .resizable()
                .scaledToFit()
                .frame(width: 75.0, height: 75.0)
        }.padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
            .frame(minWidth:  0, maxWidth: .infinity, minHeight: 0, maxHeight: 130, alignment: .topLeading)
            .background(Color("Salmon"))
            .cornerRadius(15)
    }
}

// MARK: Measuring Heart Rate Layout
struct MeasuringHeartRateView: View {
    
    var measure: Measure
    
    var body: some View {
        VStack {
            RoundedRectangle(cornerRadius: 15.0)
                .padding()
        }
        
    }
}


// MARK: AR Help Button View
struct ARHelpButtonView: View {
    
    var measure: Measure
    
    var body: some View {
        HStack {
            NavigationLink(destination: ARHelpView(measure: measure)) {
                HStack{
                    Image(systemName: "questionmark")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 15.0,height: 15.0, alignment: .center)
                }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 30, alignment: .center)
                    .padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
                    .foregroundColor(Color.black)
            }.background(Color("Salmon"))
                .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
                .cornerRadius(15)
                .frame(minHeight: 0, maxHeight: .infinity, alignment: .bottom)
        }
    }
}

struct PulseViewControllerRepresentable: UIViewControllerRepresentable {
    @Binding var secondsRemaining: Int
    @Binding var heartRateValue: Int
    @Binding var heartRateAverage: Int
    
    func makeUIViewController(context: Context) -> PulseViewController {
        let pulseViewController = PulseViewController()
        pulseViewController.delegate = context.coordinator
        return pulseViewController
    }
    
    func updateUIViewController(_ uiViewController: PulseViewController, context: Context) {
        // Update the view controller if needed
    }
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    class Coordinator: NSObject, PulseViewControllerDelegate {
        var parent: PulseViewControllerRepresentable
        
        init(_ parent: PulseViewControllerRepresentable) {
            self.parent = parent
        }
        
        func didReceiveData(_ data: [Int]) {
            parent.secondsRemaining = data[0]
            parent.heartRateValue = data[1]
            parent.heartRateAverage += data[1]
        }
    }
}



// MARK: Measure View
struct MeasureView: View{
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    var btnBack: some View { Button(action: {
        dismissView()
    }) {
        HStack {
            Image(systemName: "arrow.left")
                .resizable()
                .scaledToFit()
                .foregroundColor(.black)
                .frame(width: 15.0, height: 15.0, alignment: .leading)
            Text("Back")
        }.frame(minWidth: 0, maxWidth: .infinity, alignment: .center)
    }}
    
    var measure: Measure
    var token:String
    
    @StateObject private var heartRateManager  = HeartRateManager(cameraType: .back, preferredSpec: nil, previewContainer: nil)
    
    @State private var secondsRemaining = 30
    
    @State private var heartRateValue = 0
    @State private var heartRateAverage = 0
    
    @State private var temperatureMeasurements: [Double] = []
    @State private var temperatureValue = 0.0
    @State private var isMeasuring = false
    @State private var startClicked = false
    
    
    
    
    func measureTemperatureForDuration() {
        isMeasuring = true
        let measurementDuration: TimeInterval = 30 // Duration in seconds
        
        DispatchQueue.global().async {
            let startTime = Date()
            while Date().timeIntervalSince(startTime) < measurementDuration {
                // TODO: Get Temperature
                let apiTemperature = APITemperatureGET()
                apiTemperature.getTemperature { temperature in
                    temperatureMeasurements.append(temperature)
                    temperatureValue = temperature
                }
                Thread.sleep(forTimeInterval: 1)
                secondsRemaining = secondsRemaining - 1
            }
            
            // Calculate average temperature
            let totalTemperature = temperatureMeasurements.reduce(0, +)
            let averageTemperature = totalTemperature / Double(temperatureMeasurements.count)
            
            // Post Temperature
            let api = APITemperaturePOST()
            api.bearerToken = token
            api.makeTemperaturePostRequest(temperature: averageTemperature) { error in
                if let error = error {
                    print("Error posting heartbeat in: \(error)")
                } else {
                    print("Posted Temperature: \(averageTemperature)")
                }
            }
            
            // Update UI on the main queue
            DispatchQueue.main.async {
                temperatureValue = averageTemperature
                isMeasuring = false
            }
            
            // Perform any post-calculation tasks here
            // For instance, send the average temperature to an API
            
            //sendAverageTemperatureToAPI(averageTemperature)
        }
    }
    
    func dismissView(){
        self.presentationMode.wrappedValue.dismiss()
    }
    
    var body: some View {
        
        VStack{
            MeasuringTextView()
            Text(measure.name)
                .font(.title3)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .frame(maxWidth: .infinity,alignment: .leading)
            
            
            if let _ = measure as? Heartbeat {
                VStack {
                    
                    if secondsRemaining > 0{
                        PulseViewControllerRepresentable(secondsRemaining: $secondsRemaining, heartRateValue: $heartRateValue, heartRateAverage: $heartRateAverage)
                            .frame(minHeight: 120, maxHeight: .infinity,alignment: .center)
                            .padding(EdgeInsets(top: 30, leading: 0, bottom: 150, trailing: 0))
                        
                        if heartRateValue > 0{
                            Text("Time remaining: \(secondsRemaining) seconds")
                        }
                    } else {
                        let measuredHeartrate = Int32(self.heartRateAverage/30)
                        Text("Measurement Completed")
                            .font(.title)
                            .fontWeight(.bold)
                            .padding()
                            .onAppear(){
                                print("Value measured: \(measuredHeartrate)) BPM")
                                // Post Heartbeat
                                let api = APIHeartbeatPOST()
                                api.bearerToken = token
                                api.makeHeartRatePostRequest(heartbeat: measuredHeartrate) { error in
                                    if let error = error {
                                        print("Error posting heartbeat in: \(error)")
                                    } else {
                                        print("Posted Heartbeat: \(measuredHeartrate)")
                                    }
                                }
                                
                                DispatchQueue.main.asyncAfter(deadline: .now()+2){
                                    dismissView()
                                }
                                Text("Value measured: \(Int32(measuredHeartrate)) BPM")
                            }
                    }
                }
            } else {
                VStack{
                    if secondsRemaining > 0{
                        Image(systemName: measure.symbol)
                            .resizable()
                            .scaledToFit()
                            .frame(width: 75.0, height: 75.0)
                            .padding(EdgeInsets(top: 0, leading: 0, bottom: 20, trailing: 0))
                        Text("\(temperatureValue, specifier: "%.2f") ºC")
                            .font(.title)
                            .fontWeight(.bold)
                        Text("Time remaining: \(secondsRemaining) seconds")
                        
                        if !startClicked{
                            Button(action: {
                                startClicked = true
                                temperatureMeasurements = []
                                measureTemperatureForDuration()
                            }) {
                                HStack{
                                    Text("Start")
                                }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 30, alignment: .center)
                                    .padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
                                    .foregroundColor(Color.black)
                            }.background(Color("Salmon"))
                                .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
                                .cornerRadius(15)
                                .frame(minHeight: 0, maxHeight: .infinity, alignment: .bottom)
                        }
                    } else {
                        Text("Measurement Completed")
                            .font(.title)
                            .fontWeight(.bold)
                            .padding()
                            .onAppear(){
                                // TODO: Post Temperature
                                DispatchQueue.main.asyncAfter(deadline: .now()+2){
                                    dismissView()
                                }
                            }
                        Text("Value measured: \(String(format: "%.2f", temperatureValue)) ºC")
                    }
                }
            }
            
            ARHelpButtonView(measure: measure)
        }
        .frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
        .navigationBarHidden(false)
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: btnBack)
    }
}
