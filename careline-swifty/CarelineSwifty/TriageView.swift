//
//  TriageView.swift
//  CarelineSwifty
//
//  Created by formando on 20/11/2023.
//

import SwiftUI

struct TriageTextView: View {
    var body: some View {
        HStack(alignment: .center) {
            VStack(alignment: .leading) {
                Text("Triage")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
            }
        }
        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 50, maxHeight: 50, alignment: .leading)
    }
}

struct TriageMeasureButtonView: View{
    var measure: Measure
    var token: String
    
    var body: some View {
        NavigationLink(destination: MeasureView(measure: measure, token:token)) {
            HStack{
                Image(systemName: measure.symbol)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 30.0,height: 30.0)
                Text(measure.name)
                    .padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 0))
                    .frame(minWidth: 170, maxWidth: 170, alignment: .leading)
                VStack {
                    
                }.frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .trailing)
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 30, maxHeight: 30, alignment: .leading)
                .padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
                .foregroundColor(Color.black)
        }.background(Color("Salmon"))
            .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
            .cornerRadius(15)
        
    }
}



struct SymptonsTextView: View {
    @Binding var symptons: String
    
    private enum Field: Int, CaseIterable {
        case symptons
    }
    
    @FocusState private var focusedField: Field?
    
    var body: some View {
        VStack {
            TextEditor(text: $symptons)
                .frame(minHeight: 50, maxHeight: 150)
                .focused($focusedField, equals: .symptons)
                .padding(EdgeInsets(top: 5, leading: 5, bottom: 5, trailing: 5))
        }.overlay(
            RoundedRectangle(cornerRadius: 15).stroke(Color.gray, lineWidth: 2)
        )
        .padding(EdgeInsets(top: 0, leading: 3, bottom: 0, trailing: 3))
        .toolbar {
            ToolbarItem(placement: .keyboard) {
                Button("Done"){
                    focusedField = nil
                }
            }
        }
    }
}


struct CompletedButtonView: View {
    
    var allMeasured: Bool
    var token: String
    var symptoms: String
    let apiGetLatestHeartbeat = APIHeartbeatGET()
    let apiGetLatestTemperature = APITemperatureGETCareline()
    let apiPost = APITriagePOST()
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var body: some View {
        HStack {
            Button(action: {
                apiGetLatestHeartbeat.bearerToken = token
                apiGetLatestHeartbeat.makeHeartbeatGetRequest { result in
                    switch result {
                    case .success:
                        print("Heartbeat request successful")
                        apiGetLatestTemperature.bearerToken = token
                        apiGetLatestTemperature.makeTemperatureGetRequest { result in
                            switch result {
                            case .success:
                                print("Temperature request successful")
                                apiPost.bearerToken = token
                                print("Temperature: \(apiGetLatestTemperature.latestTemperatureValue)")
                                print("Heartbeat: \(apiGetLatestHeartbeat.latestHeartbeatValue)")
                                print("Symptoms: \(symptoms)")
                                apiPost.makeTriagePostRequest(symptoms: symptoms, temperature: apiGetLatestTemperature.latestTemperatureValue ?? 0.0, heartbeat: apiGetLatestHeartbeat.latestHeartbeatValue ?? 0) { error in
                                    if let error = error {
                                        print("Error posting heartbeat in: \(error)")
                                    } else {
                                        print("Posted Heartbeat")
                                    }
                                }
                            case .failure(let error):
                                print("Temperature request failed with error: \(error)")
                            }
                        }
                    case .failure(let error):
                        print("Heartbeat request failed with error: \(error)")
                    }
                }
                self.presentationMode.wrappedValue.dismiss()
            }) {
                HStack{
                    Text("Completed")
                        .frame(alignment: .center)
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


struct TriageView: View{
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
    
    var measures: [Measure]
    var token: String
    @State private var symptoms: String = ""
    
    var allMeasured: Bool = false
    
    var body: some View {
        VStack {
            TriageTextView()
            Text("Describe your symptons")
                .font(.title3)
                .fontWeight(.bold)
                .frame(maxWidth: .infinity,alignment: .leading)
            SymptonsTextView(symptons: $symptoms)
            Text("Measures")
                .font(.title3)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .frame(maxWidth: .infinity,alignment: .leading)
            ForEach(measures){measure in
                TriageMeasureButtonView(measure: measure, token:token)
            }
            CompletedButtonView(allMeasured: allMeasured, token:token, symptoms:symptoms)
        }.frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            .navigationBarHidden(false)
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading: btnBack)
    }
}
