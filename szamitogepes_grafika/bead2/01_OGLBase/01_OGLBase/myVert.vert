#version 330 core

// VBO-ból érkező változók
in vec3 vs_in_pos;
in vec3 vs_in_norm;
in vec2 vs_in_tex;

// a pipeline-ban tovább adandó értékek
out vec3 vs_out_pos;
out vec3 vs_out_norm;
out vec2 vs_out_tex;
out vec2 uv;

// shader külső paraméterei
uniform mat4 MVP;
uniform mat4 world;
uniform mat4 worldIT;

float x,y,z,d,x1,y1,z1,p,u,v,yx;

void main()
{
	gl_Position = MVP * vec4( vs_in_pos, 1 );
	
	vs_out_pos = (world * vec4(vs_in_pos, 1)).xyz;
	vs_out_norm = (worldIT * vec4(vs_in_norm, 0)).xyz;

	x=vs_in_pos.x;
	y=vs_in_pos.y;
	y=vs_in_pos.z;

	d=sqrt((x*x)+(y*y)+(z*z));
	x1=x/d;
	y1=y/d;
	z1=z/d;
	p=sqrt((x1*x1)+(y1*y1)+(z1*z1));
	v=acos(z1/p) /3.15;
	u=atan(y1/x1) /(3.15/2);

	vs_out_tex = vec2(u,v); //vs_in_tex;
}