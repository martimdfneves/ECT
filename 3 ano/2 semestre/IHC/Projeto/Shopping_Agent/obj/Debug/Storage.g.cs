﻿#pragma checksum "..\..\Storage.xaml" "{8829d00f-11b8-4213-878b-770e8597ac16}" "E79E545947C23225F6262798D6BAB901658452BB81F8C9A9C443E8A71BA49A80"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using Shopping_Agent;
using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Shell;


namespace Shopping_Agent {
    
    
    /// <summary>
    /// Storage
    /// </summary>
    public partial class Storage : System.Windows.Controls.Page, System.Windows.Markup.IComponentConnector {
        
        
        #line 25 "..\..\Storage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnHome;
        
        #line default
        #line hidden
        
        
        #line 26 "..\..\Storage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnPurchase;
        
        #line default
        #line hidden
        
        
        #line 27 "..\..\Storage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnStorage;
        
        #line default
        #line hidden
        
        
        #line 28 "..\..\Storage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnOrders;
        
        #line default
        #line hidden
        
        
        #line 29 "..\..\Storage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnAccount;
        
        #line default
        #line hidden
        
        
        #line 41 "..\..\Storage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.ListBox checkedList;
        
        #line default
        #line hidden
        
        
        #line 67 "..\..\Storage.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btShippingSubmit;
        
        #line default
        #line hidden
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/Shopping_Agent;component/storage.xaml", System.UriKind.Relative);
            
            #line 1 "..\..\Storage.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);
            
            #line default
            #line hidden
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target) {
            switch (connectionId)
            {
            case 1:
            this.btnHome = ((System.Windows.Controls.Button)(target));
            
            #line 25 "..\..\Storage.xaml"
            this.btnHome.Click += new System.Windows.RoutedEventHandler(this.btnHome_Click);
            
            #line default
            #line hidden
            return;
            case 2:
            this.btnPurchase = ((System.Windows.Controls.Button)(target));
            
            #line 26 "..\..\Storage.xaml"
            this.btnPurchase.Click += new System.Windows.RoutedEventHandler(this.btnPurchase_Click);
            
            #line default
            #line hidden
            return;
            case 3:
            this.btnStorage = ((System.Windows.Controls.Button)(target));
            
            #line 27 "..\..\Storage.xaml"
            this.btnStorage.Click += new System.Windows.RoutedEventHandler(this.btnStorage_Click);
            
            #line default
            #line hidden
            return;
            case 4:
            this.btnOrders = ((System.Windows.Controls.Button)(target));
            
            #line 28 "..\..\Storage.xaml"
            this.btnOrders.Click += new System.Windows.RoutedEventHandler(this.btnOrders_Click);
            
            #line default
            #line hidden
            return;
            case 5:
            this.btnAccount = ((System.Windows.Controls.Button)(target));
            
            #line 29 "..\..\Storage.xaml"
            this.btnAccount.Click += new System.Windows.RoutedEventHandler(this.btnAccount_Click);
            
            #line default
            #line hidden
            return;
            case 6:
            this.checkedList = ((System.Windows.Controls.ListBox)(target));
            return;
            case 7:
            this.btShippingSubmit = ((System.Windows.Controls.Button)(target));
            
            #line 67 "..\..\Storage.xaml"
            this.btShippingSubmit.Click += new System.Windows.RoutedEventHandler(this.btnPurchasedOrd_Click);
            
            #line default
            #line hidden
            return;
            }
            this._contentLoaded = true;
        }
    }
}

