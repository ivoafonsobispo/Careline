import { useState } from "react";
import "./Login.css";

import SignIn from "../Components/Login/SignIn";
import SignUp from "../Components/Login/SignUp";

export default function Login() {

    const [showSignUp, setShowSignUp] = useState(false);

    const handleSetOtherView = () => {
        setShowSignUp(!showSignUp);
    };

    return (
        <div className="login-body">
            <div className="login-left-column">
                <div className="login-left-content">
                    <h2 className="login-title">CareLine</h2>
                    <span>Hello</span>
                </div>
            </div>
            <div className="login-right-column">
                {!showSignUp ?
                    <SignIn setOtherView={handleSetOtherView} />
                    :
                    <SignUp setOtherView={handleSetOtherView} />
                }
            </div>
        </div>
    );
}