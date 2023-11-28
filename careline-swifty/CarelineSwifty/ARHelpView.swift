//
//  ARView.swift
//  CarelineSwifty
//
//  Created by formando on 21/11/2023.
//

import SwiftUI
import RealityKit

struct HowToReadTextView: View {
    var body: some View {
        HStack(alignment: .center) {
            VStack(alignment: .leading) {
                Text ("WEDNESDAY, OCT 18")
                    .foregroundColor(.gray)
                    .font(.caption)
                Text("How to read")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
            }
        }
        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 50, alignment: .leading)
    }
}

struct ARFrameView: UIViewRepresentable {
    
    func makeUIView(context: Context) -> some UIView {
        let view = ARView()
        return view
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        
    }
}

struct HelpPhraseView: View {
    var measure: Measure
    
    var body: some View {
        HStack(alignment: .center) {
            Text("Place the card with the ") + Text(Image(systemName: measure.symbol)) + Text(" in the front of the camera.")
            
        }
        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 50, alignment: .leading)
    }
}



struct ARHelpView: View{
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
    
    var btnDone: some View { Button(action: {
        self.presentationMode.wrappedValue.dismiss()
    }) {
        HStack {
            Text("Done")
                .foregroundColor(.red)
        }.frame(minWidth: 0, maxWidth: .infinity, alignment: .center)
    }}
    
    var measure: Measure
    
    var body: some View {
        VStack {
            HowToReadTextView()
            Text(measure.name)
                .font(.title3)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .frame(maxWidth: .infinity,alignment: .leading)
            ARFrameView()
                .ignoresSafeArea()
            HelpPhraseView(measure: measure)
        }.navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: btnBack, trailing: btnDone)
    }
}
