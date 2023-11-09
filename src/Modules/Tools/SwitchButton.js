import { useState } from 'react';
import './SwitchButton.css'


export default function SwitchButton(props) {


    function leftClick() {
        var btn = document.getElementById('btn')
        btn.style.left = '0';
        props.onStateChange(false);
    }
    
    function rightClick() {
        var btn = document.getElementById('btn')
        btn.style.left = '220px';
        props.onStateChange(true);
    }
    return (
    <div className="form-box">
		<div className ="button-box">
			<div id="btn"></div>
			<button id="left" type="button" class="toggle-btn" onClick={leftClick}>Входящие</button>
			<button id="right" type="button" class="toggle-btn" onClick={rightClick}>Исходящие</button>
		</div>
	</div>
    )
}