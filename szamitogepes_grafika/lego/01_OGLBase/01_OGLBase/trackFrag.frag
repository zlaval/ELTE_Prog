#version 330 core

in vec3 vs_out_pos;
in vec3 vs_out_norm;
in vec2 vs_out_tex;

out vec4 fs_out_col;


uniform vec3 light_dir = vec3(-1,-1,-1);

uniform vec3 La = vec3(2.4, 2.4, 2.4);
uniform vec3 Ld = vec3(0.6, 0.6, 0.6);
uniform vec3 Ls = vec3(0.9, 0.9, 0.9);

uniform vec3 Ka = vec3(0.4, 0.4, 0.3);
uniform vec3 Kd = vec3(0.5, 0.5, 0.4);
uniform vec3 Ks = vec3(0.6, 0.6, 0.5);

uniform vec3 eyePos;
uniform sampler2D texImage;
uniform vec3 isPointLight=vec3(0,0,0);

uniform vec3 carPosition;


vec3 position=vec3(0.0,3.0,30.0);
float dist;
float attenuation;
vec3 pc = vec3(0.4, 0.4, 0.5);


uniform vec3 spotPosition=vec3(10,2,0);
uniform vec3 spotDirection=vec3(10,0,0);
uniform vec3 spotEye=vec3(-1,1,0);

float spotRange=30.f;
vec3 spotAttr=vec3(1,0,0);
float spotCone=10.f;
vec4 spotAmbient=vec4(0,0,0,1);
vec4 spotDiffuse=vec4(1,1,0,1);
vec4 spotSpec=vec4(1,1,1,1);

vec4 spotAmbientCalc=vec4(0,0,0,0);
vec4 spotDiffusCalc=vec4(0,0,0,0);
vec4 sportSpeCalc=vec4(0,0,0,0);

void main()
{


	vec3 lightVec=spotPosition-vs_out_pos;
	float spotDist=length(lightVec);
	if(spotDist<spotRange){
		lightVec/=spotDist;
		spotAmbientCalc=vec4(Ka,1)*spotAmbient;
		float diffuseFactor=dot(lightVec,vs_out_norm);
		if(diffuseFactor>0.0f){
			vec3 v=reflect(-lightVec,vs_out_norm);
			float specFactor=pow(max(dot(v,spotEye),0.f),spotSpec.w);
			spotDiffusCalc=diffuseFactor*vec4(Kd,1)*spotDiffuse;
			sportSpeCalc=specFactor*vec4(Ks,1)*spotSpec;
		}
		float spot=pow(max(dot(-lightVec,spotDirection),0.f),spotCone);
		float att=spot / dot(spotAttr,vec3(1,spotDist,spotDist*spotDist));
		spotAmbientCalc*=spot;
		spotDiffusCalc*=att;
		sportSpeCalc*=att;

	}

	
    if(isPointLight.x==1){
		dist    = length(position - vs_out_pos);
		attenuation = 1.0 / (1 + 0.09f * dist + 
    		    0.032f * (dist * dist));   
			
		
      
		
		vec3 l=normalize(light_dir);
		vec3 n=normalize(vs_out_norm);
		float di=clamp(dot(-l,n),0,1);

		vec3 ambient =0.5*pc*attenuation;
		vec3 diffuse=di*pc;

		if(isPointLight.y==1 && isPointLight.z==1){
			ambient=0.8*vec3(0.9, 0.1, 0.1)*attenuation;
			diffuse=di*vec3(0.9, 0.1, 0.1);
		}

		fs_out_col = (vec4(ambient+diffuse, 1) +spotAmbientCalc+spotDiffusCalc+sportSpeCalc) * texture(texImage, vs_out_tex);
	}else{
		
		vec3 l=normalize(light_dir);
		vec3 n=normalize(vs_out_norm);
		float di=clamp(dot(-l,n),0,1);
		vec3 r=reflect(l,n);
		vec3 toEye=normalize(eyePos - vs_out_pos);
		float si= pow(clamp(dot(r,toEye),0,1),16);

		vec3 ambient = La*Ka;
		vec3 diffuse=di*Ld*Kd;
		vec3 specular = si*Ls*Ks ;

		if(isPointLight.y==1 && isPointLight.z==1){
			ambient=La*vec3(0.9, 0.1, 0.1);
			diffuse=di*Ld*vec3(0.9, 0.1, 0.1);
		}


		fs_out_col = (vec4(ambient+diffuse+specular, 1) +spotAmbientCalc+spotDiffusCalc+sportSpeCalc)* texture(texImage, vs_out_tex);
	}
	



	//vec3 ambient = La;

	//vec3 normal = normalize(vs_out_norm);
	//vec3 to_light = normalize(-light_dir);
	
	//float cosa = clamp(dot(normal, to_light), 0, 1);

	//vec3 diffuse = cosa*Ld;
	
	//fs_out_col = vec4(ambient + diffuse, 1) * texture(texImage, vs_out_tex);
}