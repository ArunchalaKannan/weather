let output;
function getData(id){
	console.log("hi");
    let xhr=new XMLHttpRequest();
	xhr.open("post", "http://localhost:8080/Weather/weather", true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.onreadystatechange=function(){
		console.log(this.readyState+"  "+this.status===200)
		if(this.readyState === 4&&this.status===200){
			output=xhr.responseText;
			output=output.replace("[","").replace("]","").split(",");
			console.log(output);
			align();
		}
}
xhr.send("code="+id);
}


function align(){
	document.getElementById("n").innerHTML=output[0];
	document.getElementById("heading").innerHTML=output[1]+"&deg; C";
	document.getElementById("f").innerHTML="Fahrenheit"+output[2]+"&deg; F";
	document.getElementById("h").innerHTML=" &nbsp;"+output[3]+"%";
	document.getElementById("s").innerHTML=output[4]+" km/h";
	document.getElementById("p").innerHTML="pressure "+output[5]+"p";	
	document.getElementById("t").innerHTML=(new Date()).getHours() + ":" + (new Date()).getMinutes() + ":" + (new Date()).getSeconds();
}
getData("Tenkasi");