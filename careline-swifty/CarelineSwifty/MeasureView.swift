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
            TemperatureAPI().getTemperature { (temperature) in
               temperatureValue = "\(temperature)"
            }
            
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
    
    @StateObject private var heartRateManager  = HeartRateManager(cameraType: .back, preferredSpec: nil, previewContainer: nil)
    
    @State private var secondsRemaining = 30
    @State private var heartRateValue = 0
    @State private var heartRateAverage = 0
    
    @State private var temperatureValue = 0.0
    
    
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
                        Text("Measurement Completed")
                            .font(.title)
                            .fontWeight(.bold)
                            .padding()
                            .onAppear(){
                                makeHeartRatePostRequest()
                                
                                DispatchQueue.main.asyncAfter(deadline: .now()+2){
                                    dismissView()
                                }
                            }
                        Text("Value meausred: \(self.heartRateAverage/30)")
                    }
                }
            } else {
                VStack{
                    Image(systemName: measure.symbol)
                        .resizable()
                        .scaledToFit()
                        .frame(width: 75.0, height: 75.0)
                        .padding(EdgeInsets(top: 0, leading: 0, bottom: 20, trailing: 0))
                    Text("\(temperatureValue, specifier: "%.2f") ºC")
                        .font(.title)
                        .fontWeight(.bold)
                    Text("Time remaining: \(secondsRemaining) seconds")
                    
                    Button(action: {
                        TemperatureAPI().getTemperature { temperature in
                            temperatureValue = temperature
                        }}) {
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
            }
            
            ARHelpButtonView(measure: measure)
        }
            .frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            .navigationBarHidden(false)
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading: btnBack)
    }
    
    func dismissView(){
        self.presentationMode.wrappedValue.dismiss()
    }
    
    func makeHeartRatePostRequest(){
        guard let url = URL(string: "http://10.20.229.2/api/patients/1/heartbeats") else {
            print("Invalid URL")
            return
        }
        
        let requestData: [String: Any] = [
            "heartbeat": String(self.heartRateAverage/30)
        ]
        
        do {
            // Convert the requestData to JSON data
            let jsonData = try JSONSerialization.data(withJSONObject: requestData)

            // Create the URLRequest with the specified URL and method
            var request = URLRequest(url: url)
            request.httpMethod = "POST"

            // Set the request body with the JSON data
            request.httpBody = jsonData

            // Set the content type to JSON
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")

            // Create a URLSession task for the request
            URLSession.shared.dataTask(with: request) { data, response, error in
                if let error = error {
                    print("Error: \(error)")
                } else if let response = response as? HTTPURLResponse {
                    if(response.statusCode == 201) {
                        print("POST request successful")
                    } else {
                        print("POST request failed with status code: \(response.statusCode)")
                    }
                }
            }.resume()
        } catch {
            print("Error converting requestData to JSON data: \(error)")
        }
        
    }
}
