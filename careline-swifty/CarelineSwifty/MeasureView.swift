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

struct PulseViewControllerWrapper: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> PulseViewController {
        return PulseViewController()
    }

    func updateUIViewController(_ uiViewController: PulseViewController, context: Context) {
        // Update the view controller if needed
    }
}



// MARK: Measure View
struct MeasureView: View{
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
   
    var btnBack: some View { Button(action: {
        self.presentationMode.wrappedValue.dismiss()
    }) {
        HStack {
            Image(systemName: "arrow.left")
                .resizable()
                .scaledToFit()
                .foregroundColor(.black)
                .frame(width: 15.0,height: 15.0, alignment: .leading)
            Text("Back")
        }.frame(minWidth: 0, maxWidth: .infinity, alignment: .center)
    }}
    
    var measure: Measure
    
    @StateObject private var heartRateManager  = HeartRateManager(cameraType: .back, preferredSpec: nil, previewContainer: nil)
   
    var body: some View {
        
        VStack{
            MeasuringTextView()
            Text(measure.name)
                .font(.title3)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .frame(maxWidth: .infinity,alignment: .leading)
            
            VStack {
                
                PulseViewControllerWrapper()
                                    .edgesIgnoringSafeArea(.all)
                Text("Time remaining: 30 seconds")
            }
            
            ARHelpButtonView(measure: measure)
        }
            .frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            .navigationBarHidden(false)
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading: btnBack)
    }
}
