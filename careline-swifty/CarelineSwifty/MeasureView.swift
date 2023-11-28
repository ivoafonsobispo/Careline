//
//  MeasureView.swift
//  CarelineSwifty
//
//  Created by formando on 17/11/2023.
//

import SwiftUI

import HealthKit

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

struct MeasureValueView: View {
    var measure: Measure
    
    var body: some View {
        HStack{
            VStack(alignment: .leading){
                Text("Reading:")
                HStack{
                    Text(measure.data)
                        .font(.custom("", fixedSize: 50))
                        .fontWeight(.bold)
                    Text(measure.metric)
                }.padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 20))
                Text ("WEDNESDAY, OCT 18 AT 15:35")
                    .foregroundColor(.gray)
                    .font(.caption)
            }
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

func authorizeHealthKit() -> HKHealthStore{
    let healthStore = HKHealthStore()
    
    let healthKitTypes: Set = [HKObjectType.quantityType(forIdentifier: HKQuantityTypeIdentifier.heartRate)!]
    
    healthStore.requestAuthorization(toShare: healthKitTypes, read: healthKitTypes) {_, _ in}
    
    return healthStore
}

func latestHeartRate() {
    
    let healthStore = authorizeHealthKit()
    
    guard let sampleType = HKObjectType.quantityType(forIdentifier: HKQuantityTypeIdentifier.heartRate) else {
        return
    }
    
    let startDate = Calendar.current.date(byAdding: .month, value: -1, to: Date())
    
    let predicate = HKQuery.predicateForSamples(withStart: startDate, end: Date(), options: .strictEndDate)
    
    let sortDescriptor = NSSortDescriptor(key: HKSampleSortIdentifierStartDate, ascending: false)
    
    let query = HKSampleQuery(sampleType: sampleType, predicate: predicate, limit: Int(HKObjectQueryNoLimit), sortDescriptors: [sortDescriptor]) {
        (sample, result, error) in
        guard error == nil else {
            return
        }
        
        let data = result![0] as! HKQuantitySample
        let unit = HKUnit(from: "count/min")
        let latestHr = data.quantity.doubleValue(for: unit)
        print("Latest Hr: \(latestHr) BPM")
        
        let dateFormator = DateFormatter()
        dateFormator.dateFormat = "dd/MM/yyyy hh:mm s"
        let StartDate = dateFormator.string(from: data.startDate)
        let EndDate = dateFormator.string(from: data.endDate)
        print("StartDate \(StartDate) : EndDate \(EndDate)")
    }
    
    healthStore.execute(query)
}

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
    
    var body: some View {
        
        VStack{
            MeasuringTextView()
            Text(measure.name)
                .font(.title3)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .frame(maxWidth: .infinity,alignment: .leading)
            MeasureValueView(measure: measure)
            ARHelpButtonView(measure: measure)
        }
            .frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            .navigationBarHidden(false)
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading: btnBack)
            .onAppear(perform: latestHeartRate)
    }
}
