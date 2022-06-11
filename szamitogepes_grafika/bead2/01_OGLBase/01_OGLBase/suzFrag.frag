#version 330 core

// pipeline-ból bejövõ per-fragment attribútumok
in vec3 vs_out_pos;
in vec3 vs_out_norm;
in vec2 vs_out_tex;
in float d;

out vec4 fs_out_col;

// irány fényforrás: fény iránya
uniform vec3 light_dir = vec3(-1,-1,-1);

// fénytulajdonságok: ambiens, diffúz, ...
uniform vec3 La = vec3(0.4, 0.4, 0.4);
uniform vec3 Ld = vec3(0.6, 0.6, 0.6);

uniform sampler2D texImage;
uniform sampler2D texImageIron;
uniform float uvVal;
vec4 texture0,texture1;


void main()
{

	vec3 ambient = La;

	vec3 normal = normalize(vs_out_norm);
	vec3 to_light = normalize(-light_dir);
	
	float cosa = clamp(dot(normal, to_light), 0, 1);

	vec3 diffuse = cosa*Ld;

	if(d>(uvVal/2)){
		fs_out_col = vec4(ambient , 1) * texture(texImageIron, vs_out_tex);
	}else{
	    texture0=(4*(1/d))*texture(texImage, vs_out_tex);
		texture1=((d))*texture(texImageIron, vs_out_tex);

		fs_out_col = vec4(ambient , 1) *( texture0 + texture1);
	}
	
	
}