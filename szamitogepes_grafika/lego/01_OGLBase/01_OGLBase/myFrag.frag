#version 330 core

// pipeline-ból bejövő per-fragment attribútumok
in vec3 vs_out_pos;
in vec3 vs_out_norm;
in vec2 vs_out_tex;
in vec4 vs_out_color;

out vec4 fs_out_col;

// irány fényforrás: fény iránya
uniform vec3 light_dir = vec3(-1,-1,-1);

// fénytulajdonságok: ambiens, diffúz, ...
uniform vec3 La = vec3(1.4, 1.4, 1.4);
uniform vec3 Ld = vec3(0.6, 0.6, 0.6);
uniform vec3 Ls = vec3(0.9, 0.9, 0.9);

uniform vec3 Ka = vec3(0.5, 0.5, 0.4);
uniform vec3 Kd = vec3(0.5, 0.5, 0.4);
uniform vec3 Ks = vec3(0.7, 0.7, 0.6);

uniform vec3 eyePos;

uniform vec3 isPointLight=vec3(0,0,0);

vec3 position=vec3(0.0,3.0,30.0);
float dist;
float attenuation;
vec3 pc = vec3(0.4, 0.4, 0.5);

void main()
{

 if(isPointLight.x==1){
		dist    = length(position - vs_out_pos);
		attenuation = 1.0 / (1 + 0.09f * dist + 
    		    0.032f * (dist * dist));   
				
		vec3 ambient =0.5*pc*attenuation;
		vec3 l=normalize(light_dir);
		vec3 n=normalize(vs_out_norm);
		float di=clamp(dot(-l,n),0,1);
		vec3 diffuse=di*pc;

		fs_out_col = vec4(ambient+diffuse, 1) *vs_out_color;
	}else{

		vec3 ambient = Ka*La;
		vec3 l=normalize(light_dir);
		vec3 n=normalize(vs_out_norm);
		float di=clamp(dot(-l,n),0,1);
		vec3 diffuse=di*Ld*Kd;
		vec3 r=reflect(l,n);
		vec3 toEye=normalize(eyePos - vs_out_pos);
		float si= pow(clamp(dot(r,toEye),0,1),16);
		vec3 specular = si*Ls*Ks ;
		fs_out_col = vec4(ambient+diffuse+specular, 1) * vs_out_color;	
	}

	//vec3 ambient = La;

	//vec3 normal = normalize(vs_out_norm);
	//vec3 to_light = normalize(-light_dir);
	
	//float cosa = clamp(dot(normal, to_light), 0, 1);

	//vec3 diffuse = cosa*Ld;
	
	//fs_out_col = vec4(ambient + diffuse, 1) * vs_out_color;
}